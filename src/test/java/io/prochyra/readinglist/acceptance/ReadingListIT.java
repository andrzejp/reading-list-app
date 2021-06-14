package io.prochyra.readinglist.acceptance;

import io.prochyra.readinglist.WireMockTest;
import io.prochyra.readinglist.domain.Book;
import io.prochyra.readinglist.domain.Catalogue;
import io.prochyra.readinglist.domain.CatalogueException;
import io.prochyra.readinglist.external.GoogleBookAdapter;
import io.prochyra.readinglist.external.GoogleBooksCatalogue;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.BDDAssertions.then;

@DisplayNameGeneration(ReplaceUnderscores.class)
class ReadingListIT extends WireMockTest {


    @Test
    void should_return_a_list_of_five_books_matching_a_query() throws CatalogueException {
        Catalogue catalogue = new GoogleBooksCatalogue("localhost:8080", new GoogleBookAdapter());
        givenThat(get(anyUrl()).willReturn(ok().withBodyFile("1984_volumes.json")));

        List<Book> matchingBooks = catalogue.find("A Book Title");

        then(matchingBooks).hasSize(5);
    }
}
