package io.prochyra.readinglist.application;

import io.prochyra.readinglist.external.console.Console;

public class SearchResultViewer extends BookListViewer {

    private static final String EMPTY_LIST_MESSAGE = "There were no results for that query.";
    private static final String TITLE = "SEARCH RESULTS";

    public SearchResultViewer(Console console) {
        super(console);
    }

    @Override
    void printHeader() {
        console.printLine(TITLE);
    }

    @Override
    void printEmptyListMessage() {
        console.printLine(EMPTY_LIST_MESSAGE);
    }
}
