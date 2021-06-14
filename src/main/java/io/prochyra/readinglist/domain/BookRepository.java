package io.prochyra.readinglist.domain;

import java.util.List;

public interface BookRepository {

    void save(Book book);

    List<Book> getAll();
}
