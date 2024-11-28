package ru.vsu.amm.java;

import java.util.Scanner;

import static ru.vsu.amm.java.TextAnalyzer.getLongestWordsWithFrequency;
import static ru.vsu.amm.java.TextAnalyzer.countWordsContaining;
import static ru.vsu.amm.java.TextAnalyzer.getUniqueWords;


public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter the text(at least 1000 characters): ");
        String inputText = in.nextLine();

        WordList wordList = new WordList(inputText);

        System.out.println("Unique words: ");
        System.out.println(getUniqueWords(wordList.getWords()));

        System.out.println("\nLongest words and their frequency: ");
        getLongestWordsWithFrequency(wordList.getWords())
                .forEach(x -> System.out.println("Word: " + x[0] + " Frequency: " + x[1]));

        System.out.println("\nEnter a word to find the count of words containing it: ");
        String searchWord = in.nextLine();
        System.out.println("Number of words contining " + searchWord + ": " +
                countWordsContaining(wordList.getWords(), searchWord));
    }
}