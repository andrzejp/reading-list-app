package io.prochyra.readinglist.application;

import io.prochyra.readinglist.external.console.Console;

import java.util.List;

public abstract class BookListViewer {

    final Console console;

    BookListViewer(Console console) {
        this.console = console;
    }

    public void show(List<Book> books) {
        printHeader();

        if (books.isEmpty()) {
            printEmptyListMessage();
        } else {
            for (var i = 0; i < books.size(); i++) {
                console.printLine((i + 1) + ". " + books.get(i));
            }
        }
    }

    abstract void printEmptyListMessage();

    abstract void printHeader();
}
