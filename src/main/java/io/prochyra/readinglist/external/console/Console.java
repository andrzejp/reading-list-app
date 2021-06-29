package io.prochyra.readinglist.external.console;

public interface Console {

    void printLine(String s);

    void newLine();

    String getLine();

    int getInt() throws ConsoleException;

    void print(String s);
}
