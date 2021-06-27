package io.prochyra.readinglist;

import io.prochyra.readinglist.domain.*;
import io.prochyra.readinglist.external.*;

import java.util.List;

public class App {

    private final Console console;
    private final Catalogue catalogue;
    private final SearchResultViewer viewer;
    private final ReadingList readingList;
    private boolean isRunning;

    public App(Console console, Catalogue catalogue, SearchResultViewer viewer, ReadingList readingList) {
        this.console = console;
        this.catalogue = catalogue;
        this.viewer = viewer;
        this.readingList = readingList;
    }

    public static void main(String[] args) {
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

    public void start() {
        isRunning = true;

        while (isRunning) {
            printMainMenu();
            switch (getMenuChoice("Enter selection (1-3)")) {
                case 1 -> viewReadingList();
                case 2 -> searchForBooks();
                case 3 -> quit();
                default -> askForValidSelection();
            }
        }
    }

    private void askForValidSelection() {
        console.newLine();
        console.printLn("Please enter a valid selection!");
        console.newLine();
    }

    private void quit() {
        console.printLn("Happy reading!");
        console.printLn("👋 Bye!");
        isRunning = false;
    }

    private void searchForBooks() {
        var query = getString("Enter query");
        List<Book> queryResults;
        try {
            queryResults = catalogue.find(query);
        } catch (CatalogueException e) {
            console.printLn("There was a problem accessing the book catalogue, please try again.");
            console.newLine();
            return;
        }
        viewer.show(queryResults);

        var choice = getMenuChoice("Add one to your reading list (1-5)? [0 = MAIN MENU]");

        if (choice == 0)
            return;

        var chosenBook = queryResults.get(choice - 1);

        readingList.save(chosenBook);
        console.printLn(chosenBook + " has been added.");
        console.newLine();
    }

    private String getString(String prompt) {
        console.print(prompt + ": ");
        var s = console.getLine();
        console.newLine();
        return s;
    }

    private void viewReadingList() {
        readingList.view();
        console.newLine();
    }

    private int getMenuChoice(String prompt) {
        console.print(prompt + ": ");
        var choice = 0;
        try {
            choice = console.getInt();
        } catch (ConsoleException e) {
            return choice;
        }
        console.newLine();
        return choice;
    }

    private void printMainMenu() {
        console.printLn("📚 READING LIST APP™️ 📚");
        console.printLn("-------------------------");
        console.newLine();
        console.printLn("1 - 📖 View Reading List");
        console.printLn("2 - 🔎 Search for books to add");
        console.printLn("3 - 🛑 Quit");
        console.newLine();
    }
}
