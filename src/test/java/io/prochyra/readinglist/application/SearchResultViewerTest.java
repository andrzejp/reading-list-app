package io.prochyra.readinglist.application;

import io.prochyra.readinglist.external.console.Console;
import io.prochyra.readinglist.application.SearchResultViewer;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(ReplaceUnderscores.class)
class SearchResultViewerTest {

    @Mock
    private Console console;

    @Test
    void should_print_suitable_empty_results_message() {
        SearchResultViewer viewer = new SearchResultViewer(console);

        viewer.printEmptyListMessage();

        then(console).should().printLn("There were no results for that query.");
    }
}