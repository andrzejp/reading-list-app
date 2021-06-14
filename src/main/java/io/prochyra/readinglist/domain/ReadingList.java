package io.prochyra.readinglist.domain;

import static java.util.Collections.emptyList;

public class ReadingList {

    private final ReadingListPrinter printer;

    public ReadingList(ReadingListPrinter printer) {
        this.printer = printer;
    }

    public void save(Book book) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

    public void view() {
        printer.print(emptyList());
    }
}
