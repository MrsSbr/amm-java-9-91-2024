package ru.vsu.amm.java;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;

public class WordList {
    private final List<WordCount> words;

    public WordList(String text) {
        Map<String, Long> wordCounts = Arrays.stream(text.split("\\s+"))
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));

        words = wordCounts.entrySet().stream()
                .map(entry -> new WordCount(entry.getKey(), entry.getValue().intValue()))
                .toList();
    }

    public WordList(ArrayList<String> arrayListWords) {
        Map<String, Long> wordCounts = arrayListWords.stream()
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));

        words = new ArrayList<>(wordCounts.entrySet().stream()
                .map(entry -> new WordCount(entry.getKey(), entry.getValue().intValue()))
                .toList());
    }

    public WordList(LinkedList<String> arrayListWords) {
        Map<String, Long> wordCounts = arrayListWords.stream()
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));

        words = new LinkedList<>(wordCounts.entrySet().stream()
                .map(entry -> new WordCount(entry.getKey(), entry.getValue().intValue()))
                .toList());
    }


    public List<WordCount> getWords() {
        return words;
    }
}