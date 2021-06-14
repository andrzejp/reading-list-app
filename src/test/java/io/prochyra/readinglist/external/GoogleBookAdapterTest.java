package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Book;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static java.util.Collections.emptyList;
import static java.util.List.of;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(ReplaceUnderscores.class)
class GoogleBookAdapterTest {

    private GoogleBookAdapter adapter;
    private Jsonb jsonb;

    private JsonObject getJsonObjectFrom(String s) {
        return jsonb.fromJson(s, JsonObject.class);
    }

    private ArrayList<Book> getList(Book... book) {
        return new ArrayList<>(Arrays.asList(book));
    }

    @BeforeEach
    void setUp() {
        adapter = new GoogleBookAdapter();
        jsonb = JsonbBuilder.create();
    }

    @Test
    void should_adapt_empty_json_object_to_empty_list() {
        var json = getJsonObjectFrom("{}");

        var books = adapter.adaptFromJson(json);

        then(books).isEmpty();
    }

    @Test
    void should_adapt_book_with_only_a_title() {
        var json = getJsonObjectFrom("""
                {
                  "items": [
                    {
                      "volumeInfo": {
                        "title": "Title"
                      }
                    }
                  ]
                }
                """);
        var expectedBooks = getList(
                new Book("Title", emptyList(), "UNKNOWN"));

        var books = adapter.adaptFromJson(json);

        then(books).isEqualTo(expectedBooks);
    }

    @Test
    void should_adapt_a_book_with_multiple_authors() {
        var json = getJsonObjectFrom("""
                {
                  "items": [
                    {
                      "volumeInfo": {
                        "title": "Title",
                        "authors": [
                          "Author One",
                          "Author Two"
                        ],
                        "publisher": "A Publisher"
                      }
                    }
                  ]
                }""");
        var expectedBooks = getList(
                new Book("Title", of("Author One", "Author Two"), "A Publisher"));

        var books = adapter.adaptFromJson(json);

        then(books).isEqualTo(expectedBooks);
    }

    @Test
    void should_adapt_several_books() {
        var json = getJsonObjectFrom("""
                {
                  "items": [
                    {
                      "volumeInfo": {
                        "title": "Title One",
                        "authors": [
                          "Author One",
                          "Author Two"
                        ],
                        "publisher": "Publisher One"
                      }
                    },
                    {
                      "volumeInfo": {
                        "title": "Title Two",
                        "authors": [
                          "Author One",
                          "Author Two"
                        ],
                        "publisher": "Publisher Two"
                      }
                    },
                    {
                      "volumeInfo": {
                        "title": "Title Three",
                        "authors": [
                          "Author One",
                          "Author Two"
                        ],
                        "publisher": "Publisher Three"
                      }
                    }
                  ]
                }
                """);
        var expectedBooks = getList(
                new Book("Title One", of("Author One", "Author Two"), "Publisher One"),
                new Book("Title Two", of("Author One", "Author Two"), "Publisher Two"),
                new Book("Title Three", of("Author One", "Author Two"), "Publisher Three"));

        var books = adapter.adaptFromJson(json);

        then(books).isEqualTo(expectedBooks);
    }

    @Test
    void should_throw_exception_on_adaptToJson() {
        var books = getList(new Book("", of(), ""));

        Throwable thrown = catchThrowable(() -> adapter.adaptToJson(books));

        then(thrown).isInstanceOf(UnsupportedOperationException.class)
                .hasMessage("Not implemented.");
    }
}