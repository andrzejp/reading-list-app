package io.prochyra.readinglist.domain;

public interface Console {

    void printLn(String s);

    void newLine();

    String getLine();

    int getInt();

    void print(String s);
}
