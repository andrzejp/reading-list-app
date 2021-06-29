package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Book;
import io.prochyra.readinglist.domain.Console;

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
                console.printLn((i + 1) + ". " + books.get(i));
            }
        }
    }

    protected abstract void printEmptyListMessage();

    abstract void printHeader();
}
