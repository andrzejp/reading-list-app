package io.prochyra.readinglist.domain;

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
        printer.print(repository.getAll());
    }
}
