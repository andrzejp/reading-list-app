package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Book;
import io.prochyra.readinglist.domain.BookListViewer;
import io.prochyra.readinglist.domain.Console;

import java.util.List;

public abstract class ConsoleViewer implements BookListViewer {

    final Console console;

    ConsoleViewer(Console console) {
        this.console = console;
    }

    @Override
    public final void show(List<Book> books) {
        printHeader();

        if (books.isEmpty()) {
            printEmptyListMessage();
        } else {
            for (int i = 0; i < books.size(); i++) {
                console.printLn((i + 1) + ". " + books.get(i));
            }
        }
    }

    protected abstract void printEmptyListMessage();

    abstract void printHeader();
}
