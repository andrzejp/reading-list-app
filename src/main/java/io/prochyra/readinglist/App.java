package io.prochyra.readinglist;

import io.prochyra.readinglist.domain.Catalogue;
import io.prochyra.readinglist.domain.CatalogueException;
import io.prochyra.readinglist.domain.Console;
import io.prochyra.readinglist.external.CommandLineConsole;
import io.prochyra.readinglist.external.GoogleBookAdapter;
import io.prochyra.readinglist.external.GoogleBooksCatalogue;
import io.prochyra.readinglist.external.SearchResultViewer;

public class App {

    private final Console console;
    private final Catalogue catalogue;
    private final SearchResultViewer viewer;

    public App(Console console, Catalogue catalogue, SearchResultViewer viewer) {
        this.console = console;
        this.catalogue = catalogue;
        this.viewer = viewer;
    }

    public static void main(String[] args) throws CatalogueException {
        var console = new CommandLineConsole();
        var bookAdapter = new GoogleBookAdapter();
        var catalogue = new GoogleBooksCatalogue(bookAdapter);
        var viewer = new SearchResultViewer(console);
        var app = new App(console, catalogue, viewer);
        app.start();
    }

    public void start() throws CatalogueException {
        var query = console.getLine();
        var queryResults = catalogue.find(query);

        viewer.show(queryResults);
    }
}
