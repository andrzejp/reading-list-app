package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Console;

public class ReadingListViewer extends BookListViewer {

    private static final String EMPTY_LIST_MESSAGE = "You have no books in your reading list!";
    private static final String TITLE = "READING LIST";

    public ReadingListViewer(Console console) {
        super(console);
    }

    @Override
    protected void printHeader() {
        console.printLn(TITLE);
    }

    @Override
    protected void printEmptyListMessage() {
        console.printLn(EMPTY_LIST_MESSAGE);
    }
}
