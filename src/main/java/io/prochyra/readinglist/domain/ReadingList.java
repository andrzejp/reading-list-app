package io.prochyra.readinglist.domain;

public class ReadingList {

    private final BookListViewer viewer;
    private final BookRepository repository;

    public ReadingList(BookListViewer viewer, BookRepository repository) {
        this.viewer = viewer;
        this.repository = repository;
    }

    public void save(Book book) {
        repository.save(book);
    }

    public void view() {
        viewer.show(repository.getAll());
    }
}
