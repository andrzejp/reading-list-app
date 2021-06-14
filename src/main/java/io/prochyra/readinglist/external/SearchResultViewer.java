package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Console;

public class SearchResultViewer extends ConsoleViewer {

    public SearchResultViewer(Console console) {
        super(console);
    }

    @Override
    protected void printEmptyListMessage() {
        console.print("There were no results for that query.");
    }

    @Override
    void printHeader() {
        console.print("SEARCH RESULTS");
        console.print("--------------");
        console.newLine();
    }
}
