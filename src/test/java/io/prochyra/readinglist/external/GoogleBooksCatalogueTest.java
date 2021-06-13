package io.prochyra.readinglist.external;

import io.prochyra.readinglist.WireMockTest;
import io.prochyra.readinglist.domain.Book;
import io.prochyra.readinglist.domain.Catalogue;
import io.prochyra.readinglist.domain.CatalogueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenExceptionOfType;

@DisplayNameGeneration(ReplaceUnderscores.class)
class GoogleBooksCatalogueTest extends WireMockTest {

    Map<String, List<Book>> expectedBooks = Map.of(
            "1984", List.of(
                    new Book("1984", "George Orwell", "Houghton Mifflin Harcourt"),
                    new Book("1984", "George Orwell", "Univ. Press of Mississippi"),
                    new Book("Older Americans Act Amendments of 1984",
                            "United States. Congress. House. Committee on Education and Labor. " +
                            "Subcommittee on Human Resources", "UNKNOWN"),
                    new Book("International Study Missions",
                            "United States. Congress. House. Select Committee on Narcotics Abuse and Control",
                            "UNKNOWN"),
                    new Book("Proceedings of The Academy of Natural Sciences Special Publication 14, 1984",
                            "UNKNOWN", "Academy of Natural Sciences")),
            "brave new world", List.of(
                    new Book("Brave New World", "Aldous Huxley", "Random House"),
                    new Book("Brave New World", "Aldous Huxley", "Longman"),
                    new Book("Brave New World and Brave New World Revisited",
                            "Aldous Huxley", "Harper Collins"),
                    new Book("Brief Candles. Four Stories.", "Aldous Huxley", "Wildside Press LLC"),
                    new Book("Brave New World Revisited", "Aldous Huxley", "Random House"))
    );
    private Catalogue catalogue;

    @BeforeEach
    void setUp() {
        catalogue = new GoogleBooksCatalogue("localhost:8080");
    }

    @Test
    void should_find_no_books_for_an_empty_query() throws CatalogueException {
        then(catalogue.find("")).isEmpty();
    }

    @ParameterizedTest(name = "Query \"{0}\" returns books from response \"{1}\"")
    @CsvSource({"1984, 1984_volumes.json", "'brave new world', bnw_volumes.json"})
    void should_find_five_matching_books_if_at_least_five_match_the_query(String query, String fileName) throws CatalogueException {
        givenThat(get("/books/v1/volumes?maxResults=5&printType=books&q=" + encode(query, UTF_8))
                .willReturn(ok().withBodyFile(fileName)));

        then(catalogue.find(query)).containsAll(expectedBooks.get(query));
    }

    @Test
    void should_throw_an_exception_on_error_response_from_Google() {
        givenThat(get(anyUrl()).willReturn(notFound()));

        thenExceptionOfType(CatalogueException.class)
                .isThrownBy(() -> catalogue.find("1984"))
                .withMessage("There was a problem accessing the Google Books API. Status code was 404");
    }
}