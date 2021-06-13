package io.prochyra.readinglist.external;

import io.prochyra.readinglist.WireMockTest;
import io.prochyra.readinglist.domain.Book;
import io.prochyra.readinglist.domain.Catalogue;
import io.prochyra.readinglist.domain.CatalogueException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static java.net.URLEncoder.encode;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.assertj.core.api.BDDAssertions.then;
import static org.assertj.core.api.BDDAssertions.thenExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
class GoogleBooksCatalogueTest extends WireMockTest {

    Map<String, List<Book>> expectedBooks = Map.of(
            "1984", List.of(
                    new Book("1984", "George Orwell", "Houghton Mifflin Harcourt"),
                    new Book("The Year Book Of World Affairs 1984", "George W Keeton", "Routledge"),
                    new Book("1984", "George Orwell", "General Press"),
                    new Book("Agricultural Sample Survey, 1991/92 (1984 E.C.)", "UNKNOWN", "UNKNOWN"),
                    new Book("Nineteen Eighty-four", "George Orwell", "Large Print Press")),
            "brave new world", List.of(
                    new Book("Brave New World", "Aldous Huxley", "Random House"),
                    new Book("Brave New World", "Aldous Huxley", "Longman"),
                    new Book("Brave New World and Brave New World Revisited", "Aldous Huxley", "Harper Collins"),
                    new Book("Brief Candles. Four Stories.", "Aldous Huxley", "Wildside Press LLC"),
                    new Book("Brave New World Revisited", "Aldous Huxley", "Random House"))
    );
    @Mock
    GoogleBookAdapter bookAdapter;
    Catalogue catalogue;

    @BeforeEach
    void setUp() {
        catalogue = new GoogleBooksCatalogue("localhost:8080", bookAdapter);
    }

    @Test
    void should_find_no_books_for_an_empty_query() throws CatalogueException {
        then(catalogue.find("")).isEmpty();
    }

    @ParameterizedTest(name = "Query \"{0}\" returns books from response \"{1}\"")
    @CsvSource({"1984, 1984_volumes.json", "'brave new world', bnw_volumes.json"})
    void should_find_five_matching_books_if_at_least_five_match_the_query(String query, String fileName) throws Exception {
        givenThat(get("/books/v1/volumes?maxResults=5&printType=books&fields=items/volumeInfo(title,authors,publisher)&q="
                      + encode(query, UTF_8))
                .willReturn(ok().withBodyFile(fileName)));

        given(bookAdapter.adaptFromJson(any())).willReturn(new ArrayList<>(expectedBooks.get(query)));

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