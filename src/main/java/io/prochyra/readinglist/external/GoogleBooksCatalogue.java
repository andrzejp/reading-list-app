package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Book;
import io.prochyra.readinglist.domain.Catalogue;

import java.util.List;

public class GoogleBooksCatalogue implements Catalogue {

    public GoogleBooksCatalogue(String hostName) {
    }

    @Override
    public List<Book> find(String query) {
        if (query.isBlank()) {
            return List.of();
        }
        return List.of(new Book(), new Book(), new Book(), new Book(), new Book());
    }
}
