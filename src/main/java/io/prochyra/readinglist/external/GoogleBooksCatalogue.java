package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Book;
import io.prochyra.readinglist.domain.Catalogue;

import java.util.List;

public class GoogleBooksCatalogue implements Catalogue {

    public GoogleBooksCatalogue(String hostName) {
    }

    @Override
    public List<Book> find(String query) {
        throw new UnsupportedOperationException("Not implemented yet!");
    }
}
