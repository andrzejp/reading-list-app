package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Book;
import io.prochyra.readinglist.domain.Console;
import io.prochyra.readinglist.domain.ReadingListPrinter;

import java.util.List;

public class ConsolePrinter implements ReadingListPrinter {

    private final Console console;

    public ConsolePrinter(Console console) {
        this.console = console;
    }

    @Override
    public void print(List<Book> books) {
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
