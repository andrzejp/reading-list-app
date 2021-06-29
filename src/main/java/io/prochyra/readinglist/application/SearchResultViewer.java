package io.prochyra.readinglist.application;

import io.prochyra.readinglist.external.console.Console;

public class SearchResultViewer extends BookListViewer {

    public SearchResultViewer(Console console) {
        super(console);
    }

    @Override
    void printEmptyListMessage() {
        console.printLn("There were no results for that query.");
    }

    @Override
    void printHeader() {
        console.printLn("SEARCH RESULTS");
    }
}
