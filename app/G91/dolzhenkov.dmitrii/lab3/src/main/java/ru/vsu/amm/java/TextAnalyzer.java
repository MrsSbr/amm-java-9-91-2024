package ru.vsu.amm.java;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class TextAnalyzer {
    private final List<String> words;

    public TextAnalyzer(String text) {
        words = Arrays.stream(text.split("\\s+")).collect(Collectors.toList());
    }

    //уникальные слова
    public Set<String> getUniqueWords() {
        return words.stream().distinct().collect(Collectors.toSet());

        //return new HashSet<>(words);
    }

    //самые длинные слова и их количеситво
    public List<String[]> getLongestWordsWithFrequency() {
        int maxLength = words.stream().mapToInt(String::length).max().orElse(0);
        return words.stream()
                .filter(word -> word.length() == maxLength)
                .distinct()
                .map(word -> new String[]{word, String.valueOf(Collections.frequency(words, word))})
                .collect(Collectors.toList());
    }

    //количество слов которые содеражат слово пользователя
    public long countWordsContaining(String string) {
        return words.stream().filter(word -> word.contains(string)).count();
    }
}