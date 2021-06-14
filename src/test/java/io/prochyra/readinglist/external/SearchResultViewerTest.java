package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Console;
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

        then(console).should().print("There were no results for that query.");
    }
}