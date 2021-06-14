package io.prochyra.readinglist.domain;

import java.util.List;
import java.util.Objects;

public class Book {

    private final String title;
    private final List<String> authors;
    private final String publisher;

    public Book(String title, List<String> authors, String publisher) {
        this.title = title;
        this.authors = authors;
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Book{" +
               "title='" + title + '\'' +
               ", author='" + authors + '\'' +
               ", publisher='" + publisher + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var book = (Book) o;
        return title.equals(book.title) && authors.equals(book.authors) && publisher.equals(book.publisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, authors, publisher);
    }
}
