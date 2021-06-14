package io.prochyra.readinglist;

import io.prochyra.readinglist.domain.Book;
import io.prochyra.readinglist.domain.Catalogue;
import io.prochyra.readinglist.domain.CatalogueException;
import io.prochyra.readinglist.domain.Console;
import io.prochyra.readinglist.external.CommandLineConsole;
import io.prochyra.readinglist.external.GoogleBookAdapter;
import io.prochyra.readinglist.external.GoogleBooksCatalogue;

import java.util.List;

public class App {

    private final Console console;
    private final Catalogue catalogue;

    public App(Console console, Catalogue catalogue) {
        this.console = console;
        this.catalogue = catalogue;
    }

    public static void main(String[] args) throws CatalogueException {
        var console = new CommandLineConsole();
        var bookAdapter = new GoogleBookAdapter();
        var catalogue = new GoogleBooksCatalogue(bookAdapter);
        var app = new App(console, catalogue);
        app.start();
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
