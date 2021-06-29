package io.prochyra.readinglist.external.console;

import java.util.Scanner;

public class CommandLineConsole implements Console {

    private final Scanner scanner = new Scanner(System.in);

    @Override
    public void printLine(String s) {
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
    public int getInt() throws ConsoleException {
        var i = 0;
        try {
            i = scanner.nextInt();
        } catch (Exception e) {
            scanner.nextLine();
            throw new ConsoleException("There was a problem getting the next integer.", e);
        }
        scanner.nextLine();
        return i;
    }

    @Override
    public void print(String s) {
        System.out.print(s);
    }
}
