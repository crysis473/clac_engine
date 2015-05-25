package com.calculator.commandLine;

import java.util.Scanner;

/**
 * Reads user instructions from the command line.
 */

public class ConsoleReader {

    Scanner scanner;

    public ConsoleReader() {
        scanner = new Scanner(System.in);
    }

    public String readFromConsole() {
        String input;
        input = scanner.nextLine();
        return input;
    }

}
