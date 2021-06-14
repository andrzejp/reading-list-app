package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Book;
import io.prochyra.readinglist.domain.Console;
import io.prochyra.readinglist.domain.ReadingListViewer;

import java.util.List;

public class ConsoleViewer implements ReadingListViewer {

    private final Console console;

    public ConsoleViewer(Console console) {
        this.console = console;
    }

    @Override
    public void show(List<Book> books) {
        console.print("READING LIST");
        console.print("------------");
        console.newLine();

        if (books.isEmpty()) {
            console.print("You have no books in your reading list!");
        } else {
            for (int i = 0; i < books.size(); i++) {
                console.print((i + 1) + ". " + books.get(i));
            }
        }
    }
}
