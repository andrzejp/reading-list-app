package io.prochyra.readinglist.acceptance;

import io.prochyra.readinglist.WireMockTest;
import io.prochyra.readinglist.domain.Book;
import io.prochyra.readinglist.domain.Catalogue;
import io.prochyra.readinglist.domain.CatalogueException;
import io.prochyra.readinglist.external.GoogleBooksCatalogue;
import jakarta.json.JsonObject;
import jakarta.json.bind.adapter.JsonbAdapter;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.BDDAssertions.then;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
class ReadingListIT extends WireMockTest {

    @Mock
    private JsonbAdapter<ArrayList<Book>, JsonObject> bookAdapter;

    @Test
    void should_return_a_list_of_five_books_matching_a_query() throws CatalogueException {
        Catalogue catalogue = new GoogleBooksCatalogue("localhost:8080", bookAdapter);
        givenThat(get(anyUrl()).willReturn(ok().withBodyFile("1984_volumes.json")));

        List<Book> matchingBooks = catalogue.find("A Book Title");

        then(matchingBooks).hasSize(5);
    }
}
