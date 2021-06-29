package io.prochyra.readinglist.application;

import io.prochyra.readinglist.external.repo.BookRepository;

public class ReadingList {

    private final ReadingListViewer viewer;
    private final BookRepository repository;

    public ReadingList(ReadingListViewer viewer, BookRepository repository) {
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
