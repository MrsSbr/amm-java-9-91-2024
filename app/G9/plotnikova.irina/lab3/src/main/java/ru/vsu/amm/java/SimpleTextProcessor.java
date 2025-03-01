package ru.vsu.amm.java;

import java.util.*;
import java.util.stream.Collectors;

public class SimpleTextProcessor implements TextProcessor {
    private final String text;

    public SimpleTextProcessor(String text) {
        this.text = text;
    }

    @Override
    public Set<String> getUniqueWords() {
        return Arrays.stream(text.split("\\s+"))
                .map(word -> word.toLowerCase().replaceAll("[^a-zA-Za-яА-Я]", ""))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.toSet());
    }

    @Override
    public List<String> getLongestWordsFrequency() {
        List<String> words = Arrays.stream(text.split("\\s+"))
                .map(word -> word.toLowerCase().replaceAll("[^a-zA-Za-яА-Я]", ""))
                .filter(word -> !word.isEmpty())
                .collect(Collectors.toList());

        int maxLength = words.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);

        return words.stream()
                .filter(word -> word.length() == maxLength)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public long countWordsContaining(String substring) {
        return Arrays.stream(text.split("\\s+"))
                .map(word -> word.toLowerCase().replaceAll("[^a-zA-Za-яА-Я]", ""))
                .filter(word -> word.contains(substring.toLowerCase()))
                .count();
    }

    @Override
    public Object process(TextProcessingMode mode, String parameter) {

        switch (mode) {
            case UNIQUE_WORDS:
                return getUniqueWords();
            case LONGEST_WORDS:
                return getLongestWordsFrequency();
            case WORDS_CONTAINING:
                return countWordsContaining(parameter);
            default:
                throw new IllegalArgumentException("Unknown mode: " + mode);
        }
    }
}
