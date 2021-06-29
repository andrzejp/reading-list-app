package io.prochyra.readinglist.external.console;

public interface Console {

    void printLn(String s);

    void newLine();

    String getLine();

    int getInt() throws ConsoleException;

    void print(String s);
}
