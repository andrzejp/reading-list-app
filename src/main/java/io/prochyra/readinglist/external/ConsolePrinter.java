package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Book;
import io.prochyra.readinglist.domain.Console;
import io.prochyra.readinglist.domain.ReadingListPrinter;

import java.util.List;

public class ConsolePrinter implements ReadingListPrinter {

    public ConsolePrinter(Console console) {
    }

    @Override
    public void print(List<Book> books) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}
