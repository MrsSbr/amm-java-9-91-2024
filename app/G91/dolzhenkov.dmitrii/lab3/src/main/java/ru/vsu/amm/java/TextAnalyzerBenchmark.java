package ru.vsu.amm.java;

import java.util.*;

public class TextAnalyzerBenchmark {

    public static void main(String[] args) {
        String inputText = generateLargeText(10000);

        // ArrayList
        List<String> arrayListWords = Arrays.asList(inputText.split("\\s+"));
        TextAnalyzer arrayListAnalyzer = new TextAnalyzer(String.valueOf(new ArrayList<>(arrayListWords)));
        System.out.println("Performance with ArrayList:");
        runBenchmark(arrayListAnalyzer);

        // LinkedList
        TextAnalyzer linkedListAnalyzer = new TextAnalyzer(String.valueOf(new LinkedList<>(arrayListWords)));
        System.out.println("Performance with LinkedList:");
        runBenchmark(linkedListAnalyzer);
    }

    private static void runBenchmark(TextAnalyzer analyzer) {
        long startTime, endTime;

        // уникальные слова
        startTime = System.nanoTime();
        Set<String> uniqueWords = analyzer.getUniqueWords();
        endTime = System.nanoTime();
        System.out.println("Time to get unique words: " + (endTime - startTime) + " ns");

        // максимальная длина с количеством
        startTime = System.nanoTime();
        List<String[]> longestWordsWithFrequency = analyzer.getLongestWordsWithFrequency();
        endTime = System.nanoTime();
        System.out.println("Time to get longest words with frequency: " + (endTime - startTime) + " ns");

        // содержание слова
        startTime = System.nanoTime();
        long countContaining = analyzer.countWordsContaining("sample");
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
