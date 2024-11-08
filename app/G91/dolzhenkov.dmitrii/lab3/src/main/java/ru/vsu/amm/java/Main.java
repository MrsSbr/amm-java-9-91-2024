package ru.vsu.amm.java;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the text(at least 1000 characters): ");
        String inputText = in.nextLine();

        TextAnalyzer ta = new TextAnalyzer(inputText);

        System.out.println("Unique words: ");
        System.out.println(ta.getUniqueWords());

        System.out.println("\nLongest words and their frequency: ");
        ta.getLongestWordsWithFrequency()
                .forEach(x -> System.out.println("Word: " + x[0] + " Frequency: " + x[1]));

        System.out.println("\nEnter a word to find the count of words containing it: ");
        String searchWord = in.nextLine();
        System.out.println("Number of words contining \"" + searchWord
                + "\": " + ta.countWordsContaining(searchWord));
    }
}