package io.prochyra.readinglist;

import io.prochyra.readinglist.domain.Book;
import io.prochyra.readinglist.domain.Catalogue;
import io.prochyra.readinglist.domain.CatalogueException;
import io.prochyra.readinglist.domain.Console;

import java.util.List;

public class App {

    private final Console console;
    private final Catalogue catalogue;

    public App(Console console, Catalogue catalogue) {
        this.console = console;
        this.catalogue = catalogue;
    }

    public void start() throws CatalogueException {
        String query = console.getLine();
        List<Book> queryResults = catalogue.find(query);

        for (int i = 0, queryResultsSize = queryResults.size(); i < queryResultsSize; i++) {
            var book = queryResults.get(i);
            console.print((i + 1) + ". " + book.toString());
        }
    }
}
