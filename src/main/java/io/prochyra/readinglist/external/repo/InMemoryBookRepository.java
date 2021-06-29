package io.prochyra.readinglist.external.repo;

import io.prochyra.readinglist.application.Book;

import java.util.ArrayList;
import java.util.List;

import static java.util.List.copyOf;

public class InMemoryBookRepository implements BookRepository {

    private final List<Book> books = new ArrayList<>();

    @Override
    public void save(Book book) {
        books.add(book);
    }

    @Override
    public List<Book> getAll() {
        return copyOf(books);
    }
}
