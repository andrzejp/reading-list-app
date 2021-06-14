package io.prochyra.readinglist.external;

import io.prochyra.readinglist.domain.Console;

import java.util.Scanner;

public class CommandLineConsole implements Console {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void printLn(String s) {
        System.out.println(s);
    }

    @Override
    public void newLine() {
        System.out.println();
    }

    @Override
    public String getLine() {
        return scanner.nextLine();
    }

    @Override
    public int getInt() {
        var i = scanner.nextInt();
        scanner.nextLine();
        return i;
    }

    @Override
    public void print(String s) {
        System.out.print(s);
    }
}
