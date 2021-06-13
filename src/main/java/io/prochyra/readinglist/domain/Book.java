package io.prochyra.readinglist.domain;

import java.util.Objects;

public class Book {

    private final String title;
    private final String author;
    private final String publisher;

    public Book(String title, String author, String publisher) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Book{" +
               "title='" + title + '\'' +
               ", author='" + author + '\'' +
               ", publisher='" + publisher + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        var book = (Book) o;
        return title.equals(book.title) && author.equals(book.author) && publisher.equals(book.publisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, publisher);
    }
}
