package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Book;
import io.prochyra.readinglist.domain.Console;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Collections.emptyList;
import static java.util.List.of;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
class ConsoleViewerTest {

    @Mock
    private Console console;
    private ConsoleViewer viewer;

    @BeforeEach
    void setUp() {
        viewer = new ConsoleViewer(console);
    }

    @Test
    void should_show_a_heading() {
        var viewer = new ConsoleViewer(console);

        viewer.show(emptyList());

        then(console).should().print("READING LIST");
        then(console).should().print("------------");
        then(console).should().newLine();
    }

    @Test
    void should_show_a_message_when_the_list_is_empty() {
        viewer.show(emptyList());

        then(console).should().print("You have no books in your reading list!");
    }

    @Test
    void should_show_a_list_of_books() {
        var books = of(
                new Book("First Book", of("First Author One", "First Author Two"), "First Publisher"),
                new Book("Second Book", of("Second Author"), "Second Publisher"),
                new Book("Third Book", of("Third Author"), "Third Publisher")
        );

        viewer.show(books);

        then(console).should().print("1. 'First Book' by First Author One, First Author Two - First Publisher");
        then(console).should().print("2. 'Second Book' by Second Author - Second Publisher");
        then(console).should().print("3. 'Third Book' by Third Author - Third Publisher");
    }
}