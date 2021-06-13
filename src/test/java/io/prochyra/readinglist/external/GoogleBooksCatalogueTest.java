package io.prochyra.readinglist.external;

import io.prochyra.readinglist.WireMockTest;
import io.prochyra.readinglist.domain.Catalogue;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(ReplaceUnderscores.class)
class GoogleBooksCatalogueTest extends WireMockTest {

    @Test
    void should_find_no_books_for_an_empty_query() {
        GoogleBooksCatalogue catalogue = new GoogleBooksCatalogue("");
        then(catalogue.find("")).isEmpty();
    }

    @Test
    void should_find_five_books_if_at_least_five_books_match_the_query() {
        Catalogue catalogue = new GoogleBooksCatalogue("localhost:8080");

        givenThat(get("/books/v1/volumes?maxResults=5&printType=books&q=1984")
                .willReturn(ok().withBodyFile("volumes.json")));

        then(catalogue.find("1984")).hasSize(5);
    }
}