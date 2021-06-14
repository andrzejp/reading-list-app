package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Console;

public class SearchResultViewer extends ConsoleViewer {

    public SearchResultViewer(Console console) {
        super(console);
    }

    @Override
    protected void printEmptyListMessage() {
        console.printLn("There were no results for that query.");
    }

    @Override
    void printHeader() {
        console.printLn("SEARCH RESULTS");
        console.printLn("--------------");
        console.newLine();
    }
}
