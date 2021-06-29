package io.prochyra.readinglist.external.repo;

import io.prochyra.readinglist.application.Book;

import java.util.List;

public interface BookRepository {

    void save(Book book);

    List<Book> getAll();
}
