package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Book;
import jakarta.json.JsonObject;
import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static java.util.List.of;
import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(ReplaceUnderscores.class)
class GoogleBookAdapterTest {

    @Test
    void should_adapt_empty_json_object_to_empty_list() {
        GoogleBookAdapter adapter = new GoogleBookAdapter();
        Jsonb jsonb = JsonbBuilder.create();
        String emptyJsonObject = "{}";
        JsonObject json = jsonb.fromJson(emptyJsonObject, JsonObject.class);

        then(adapter.adaptFromJson(json)).isEmpty();
    }

    @Test
    void should_adapt_book_with_only_a_title() {
        GoogleBookAdapter adapter = new GoogleBookAdapter();
        Jsonb jsonb = JsonbBuilder.create();
        String onlyTitle = """
                {
                  "items": [
                    {
                      "volumeInfo": {
                        "title": "Title"
                      }
                    }
                  ]
                }
                """;

        JsonObject json = jsonb.fromJson(onlyTitle, JsonObject.class);

        ArrayList<Book> expectedBooks = new ArrayList<>(of(new Book("Title", of(), "UNKNOWN")));

        then(adapter.adaptFromJson(json)).isEqualTo(expectedBooks);
    }

    @Test
    void should_adapt_a_book_with_multiple_authors() {
        GoogleBookAdapter adapter = new GoogleBookAdapter();
        Jsonb jsonb = JsonbBuilder.create();
        String multipleAuthors = """
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
                }""";


        JsonObject json = jsonb.fromJson(multipleAuthors, JsonObject.class);

        ArrayList<Book> expectedBooks = new ArrayList<>(
                of(new Book("Title", of("Author One", "Author Two"), "A Publisher")));

        then(adapter.adaptFromJson(json)).isEqualTo(expectedBooks);
    }
}