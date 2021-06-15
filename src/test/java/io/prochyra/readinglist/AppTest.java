package io.prochyra.readinglist;

import io.prochyra.readinglist.domain.*;
import io.prochyra.readinglist.external.SearchResultViewer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.List.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.inOrder;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
class AppTest {

    @Mock
    Console console;
    @Mock
    private Catalogue catalogue;
    @Mock
    private ReadingList readingList;
    private App app;

    @BeforeEach
    void setUp() {
        SearchResultViewer viewer = new SearchResultViewer(console);
        app = new App(console, catalogue, viewer, readingList);
    }

    @Test
    void should_display_a_main_menu() throws CatalogueException, ConsoleException {
        InOrder inOrder = inOrder(console);

        given(console.getInt()).willReturn(3);

        app.start();

        then(console).should(inOrder).printLn("📚 READING LIST APP™️ 📚");
        then(console).should(inOrder).printLn("-------------------------");
        then(console).should(inOrder).newLine();
        then(console).should(inOrder).printLn("1 - 📖 View Reading List");
        then(console).should(inOrder).printLn("2 - 🔎 Search for books to add");
        then(console).should(inOrder).printLn("3 - 🛑 Quit");
        then(console).should(inOrder).newLine();
        then(console).should(inOrder).print("Enter selection (1-3): ");
    }

    @Test
    void should_display_reading_list() throws CatalogueException, ConsoleException {
        given(console.getInt()).willReturn(1, 3);

        app.start();

        then(readingList).should().view();
    }

    @Test
    void should_accept_a_query_and_display_the_result() throws CatalogueException, ConsoleException {
        InOrder inOrder = inOrder(console);
        given(console.getInt()).willReturn(2, 3);
        given(console.getLine()).willReturn("Book");
        given(catalogue.find("Book"))
                .willReturn(of(
                        new Book("First Book", of("First Author One", "First Author Two"), "First Publisher"),
                        new Book("Second Book", of("Second Author"), "Second Publisher"),
                        new Book("Third Book", of("Third Author"), "Third Publisher")));

        app.start();

        then(console).should(inOrder).print("Enter query: ");

        then(console).should(inOrder).printLn("SEARCH RESULTS");
        then(console).should(inOrder).printLn("--------------");
        then(console).should(inOrder).newLine();
        then(console).should(inOrder).printLn("1. 'First Book' by First Author One, First Author Two - First Publisher");
        then(console).should(inOrder).printLn("2. 'Second Book' by Second Author - Second Publisher");
        then(console).should(inOrder).printLn("3. 'Third Book' by Third Author - Third Publisher");
    }

    @Test
    void should_prompt_for_valid_selection_on_non_integer_menu_input() throws CatalogueException, ConsoleException {
        given(console.getInt())
                .willThrow(new ConsoleException("There was a problem getting the next integer.", new Exception()))
                .willReturn(3);

        app.start();

        then(console).should().printLn("Please enter a valid selection!");
    }

    @Test
    void should_show_message_on_problem_accessing_catalogue() throws ConsoleException, CatalogueException {
        given(console.getInt()).willReturn(2, 3);
        given(catalogue.find(any())).willThrow(new CatalogueException("Error message", new Exception()));

        app.start();

        then(console).should().printLn("There was a problem accessing the book catalogue, please try again.");
    }
}
