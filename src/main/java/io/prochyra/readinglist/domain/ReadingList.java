package io.prochyra.readinglist.domain;

import static java.util.Collections.emptyList;

public class ReadingList {

    private final ReadingListPrinter printer;
    private final BookRepository repository;

    public ReadingList(ReadingListPrinter printer, BookRepository repository) {
        this.printer = printer;
        this.repository = repository;
    }

    public void save(Book book) {
        repository.save(book);
    }

    public void view() {
        printer.print(emptyList());
    }
}
