package io.prochyra.readinglist.external.catalogue;

import io.prochyra.readinglist.application.Book;

import java.util.List;

public interface Catalogue {

    List<Book> find(String query) throws CatalogueException;
}
