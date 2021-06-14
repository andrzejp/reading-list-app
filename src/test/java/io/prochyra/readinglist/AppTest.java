package io.prochyra.readinglist;

import io.prochyra.readinglist.domain.Book;
import io.prochyra.readinglist.domain.Catalogue;
import io.prochyra.readinglist.domain.CatalogueException;
import io.prochyra.readinglist.domain.Console;
import io.prochyra.readinglist.external.SearchResultViewer;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.List.of;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
class AppTest {

    @Mock
    Console console;
    @Mock
    private Catalogue catalogue;

    @Test
    void should_accept_a_query_and_display_the_result() throws CatalogueException {
        SearchResultViewer viewer = new SearchResultViewer(console);
        App app = new App(console, catalogue, viewer);

        given(console.getLine()).willReturn("Book");
        given(catalogue.find("Book"))
                .willReturn(of(
                        new Book("First Book", of("First Author One", "First Author Two"), "First Publisher"),
                        new Book("Second Book", of("Second Author"), "Second Publisher"),
                        new Book("Third Book", of("Third Author"), "Third Publisher")));

        app.start();

        then(console).should().print("SEARCH RESULTS");
        then(console).should().print("--------------");
        then(console).should().newLine();
        then(console).should().print("1. 'First Book' by First Author One, First Author Two - First Publisher");
        then(console).should().print("2. 'Second Book' by Second Author - Second Publisher");
        then(console).should().print("3. 'Third Book' by Third Author - Third Publisher");
    }
}
