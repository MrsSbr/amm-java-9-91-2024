package ru.vsu.amm.java;

import java.util.Scanner;

public class InputHandler {
    private final Scanner scanner;

    public InputHandler() {
        this.scanner = new Scanner(System.in);
    }

    public String getTextInput() {
        System.out.println("Введите текст (не менее 1000 символов):");
        return scanner.nextLine();
    }

    public String getSearchWord() {
        System.out.println("Введите слово для поиска:");
        return scanner.nextLine();
    }
}
