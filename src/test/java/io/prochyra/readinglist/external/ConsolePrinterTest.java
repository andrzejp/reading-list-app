package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Console;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Collections.emptyList;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
class ConsolePrinterTest {

    @Mock
    private Console console;

    @Test
    void should_print_a_heading() {
        var printer = new ConsolePrinter(console);

        printer.print(emptyList());

        then(console).should().print("READING LIST");
        then(console).should().print("------------");
        then(console).should().newLine();
    }

    @Test
    void should_print_a_message_when_the_list_is_empty() {
        var printer = new ConsolePrinter(console);

        printer.print(emptyList());

        then(console).should().print("You have no books in your reading list!");
    }
}