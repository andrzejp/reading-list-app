package io.prochyra.readinglist.application;

import io.prochyra.readinglist.external.console.Console;

public class ReadingListViewer extends BookListViewer {

    private static final String EMPTY_LIST_MESSAGE = "You have no books in your reading list!";
    private static final String TITLE = "READING LIST";

    public ReadingListViewer(Console console) {
        super(console);
    }

    @Override
    void printHeader() {
        console.printLn(TITLE);
    }

    @Override
    void printEmptyListMessage() {
        console.printLn(EMPTY_LIST_MESSAGE);
    }
}
