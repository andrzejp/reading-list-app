package io.prochyra.readinglist;

import io.prochyra.readinglist.domain.Catalogue;
import io.prochyra.readinglist.domain.CatalogueException;
import io.prochyra.readinglist.domain.Console;
import io.prochyra.readinglist.domain.ReadingList;
import io.prochyra.readinglist.external.*;

public class App {

    private final Console console;
    private final Catalogue catalogue;
    private final SearchResultViewer viewer;
    private final ReadingList readingList;

    public App(Console console, Catalogue catalogue, SearchResultViewer viewer, ReadingList readingList) {
        this.console = console;
        this.catalogue = catalogue;
        this.viewer = viewer;
        this.readingList = readingList;
    }

    public static void main(String[] args) throws CatalogueException {
        var console = new CommandLineConsole();
        var bookAdapter = new GoogleBookAdapter();
        var catalogue = new GoogleBooksCatalogue(bookAdapter);
        var searchResultViewer = new SearchResultViewer(console);
        var readingListViewer = new ReadingListViewer(console);
        var repository = new InMemoryBookRepository();
        var readingList = new ReadingList(readingListViewer, repository);
        var app = new App(console, catalogue, searchResultViewer, readingList);
        app.start();
    }

    public void start() throws CatalogueException {
        printMainMenu();

        var choice = console.getInt();

        if (choice == 1) {
            console.newLine();
            readingList.view();
        } else {
            console.newLine();
            console.print("Enter query: ");
            var query = console.getLine();
            var queryResults = catalogue.find(query);
            console.newLine();
            viewer.show(queryResults);
        }
    }

    private void printMainMenu() {
        console.printLn("📚 READING LIST APP™️ 📚");
        console.printLn("-------------------------");
        console.newLine();
        console.printLn("1 - 📖 View Reading List");
        console.printLn("2 - 🔎 Search for books to add");
        console.printLn("3 - 🛑 Quit");
        console.newLine();
        console.print("Enter selection (1-3): ");
    }
}
