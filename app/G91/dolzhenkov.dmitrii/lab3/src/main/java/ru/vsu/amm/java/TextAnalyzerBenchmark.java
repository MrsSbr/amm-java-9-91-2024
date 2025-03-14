package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static ru.vsu.amm.java.TextAnalyzer.countWordsContaining;
import static ru.vsu.amm.java.TextAnalyzer.getLongestWordsWithFrequency;
import static ru.vsu.amm.java.TextAnalyzer.getUniqueWords;

public final class TextAnalyzerBenchmark {

    public static void main(String[] args) {
        String inputText = generateLargeText(10000);

        // ArrayList
        List<String> arrayListWords = Arrays.asList(inputText.split("\\s+"));
        WordList arrayListAnalyzer = new WordList(new ArrayList<>(arrayListWords));
        System.out.println("Performance with ArrayList:");
        runBenchmark(arrayListAnalyzer);

        // LinkedList
        WordList linkedListAnalyzer = new WordList(new LinkedList<>(arrayListWords));
        System.out.println("Performance with LinkedList:");
        runBenchmark(linkedListAnalyzer);
    }

    private static void runBenchmark(WordList wordList) {
        long startTime, endTime;

        // уникальные слова
        startTime = System.nanoTime();
        List<String> uniqueWords = getUniqueWords(wordList.getWords());
        endTime = System.nanoTime();
        System.out.println("Time to get unique words: " + (endTime - startTime) + " ns");

        // максимальная длина с количеством
        startTime = System.nanoTime();
        List<WordCount> longestWordsWithFrequency = getLongestWordsWithFrequency(wordList.getWords());
        endTime = System.nanoTime();
        System.out.println("Time to get longest words with frequency: " + (endTime - startTime) + " ns");

        // содержание слова
        startTime = System.nanoTime();
        long countContaining = countWordsContaining(wordList.getWords(), "sample");
        endTime = System.nanoTime();
        System.out.println("Time to count words containing substring: " + (endTime - startTime) + " ns");
        System.out.println();
    }

    private static String generateLargeText(int size) {
        StringBuilder text = new StringBuilder();
        String[] sampleWords = {"apple", "banana", "orange", "kiwi", "grape", "strawberry"};
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            text.append(sampleWords[random.nextInt(sampleWords.length)]).append(" ");
        }
        return text.toString();
    }
}
