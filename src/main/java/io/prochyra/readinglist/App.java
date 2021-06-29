package io.prochyra.readinglist;

import io.prochyra.readinglist.application.Book;
import io.prochyra.readinglist.application.ReadingList;
import io.prochyra.readinglist.application.ReadingListViewer;
import io.prochyra.readinglist.application.SearchResultViewer;
import io.prochyra.readinglist.external.catalogue.Catalogue;
import io.prochyra.readinglist.external.catalogue.CatalogueException;
import io.prochyra.readinglist.external.catalogue.GoogleBookAdapter;
import io.prochyra.readinglist.external.catalogue.GoogleBooksCatalogue;
import io.prochyra.readinglist.external.console.CommandLineConsole;
import io.prochyra.readinglist.external.console.Console;
import io.prochyra.readinglist.external.console.ConsoleException;
import io.prochyra.readinglist.external.repo.InMemoryBookRepository;

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
            switch (getMenuChoice("Enter selection", 1, 3)) {
                case 1 -> viewReadingList();
                case 2 -> searchForBooks();
                case 3 -> quit();
                default -> askForValidSelection();
            }
        }
    }

    private void printMainMenu() {
        console.newLine();
        console.printLine("📚 READING LIST APP™️ 📚");
        console.printLine("1 - 📖 View Reading List");
        console.printLine("2 - 🔎 Search for books to add");
        console.printLine("3 - 🛑 Quit");
    }

    private int getMenuChoice(String prompt, int first, int last) {
        while (true) {
            var choice = 0;

            console.newLine();
            console.print(prompt + " (" + first + "-" + last + "): ");

            try {
                choice = console.getInt();
            } catch (ConsoleException e) {
                console.newLine();
                askForValidSelection();
                continue;
            }
            console.newLine();

            if (choice >= first && choice <= last)
                return choice;

            askForValidSelection();
        }
    }

    private void askForValidSelection() {
        console.printLine("Please enter a valid selection!");
    }

    private void viewReadingList() {
        readingList.view();
    }

    private void searchForBooks() {
        var query = getQuery();
        List<Book> queryResults;
        try {
            queryResults = catalogue.find(query);
        } catch (CatalogueException e) {
            console.printLine("There was a problem accessing the book catalogue, please try again.");
            return;
        }
        viewer.show(queryResults);

        if (queryResults.isEmpty())
            return;

        manageReadingList(queryResults);
    }

    private String getQuery() {
        console.print("Enter query" + ": ");
        var s = console.getLine();
        console.newLine();
        return s;
    }

    private void manageReadingList(List<Book> queryResults) {
        var choice = getMenuChoice("Add one to your reading list? [0 = MAIN MENU]", 0, queryResults.size());

        if (choice == 0)
            return;

        var chosenBook = queryResults.get(choice - 1);

        readingList.save(chosenBook);
        console.printLine("[" + chosenBook + "] has been added.");
    }

    private void quit() {
        console.printLine("Happy reading!");
        console.printLine("👋 Bye!");
        console.newLine();
        isRunning = false;
    }
}
