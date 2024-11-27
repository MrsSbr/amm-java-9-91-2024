package ru.vsu.amm.java;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public final class TextAnalyzer {
    //уникальные слова
    public static List<String> getUniqueWords(List<String> words) {
        return words.stream()
                .distinct()
                .toList();
    }

    //самые длинные слова и их количеситво
    public static List<String[]> getLongestWordsWithFrequency(List<String> words) {
        int maxLength = words.stream()
                .mapToInt(String::length)
                .max()
                .orElse(0);

        List<String[]> result;
        if (maxLength == 0) {
            result = null;
        } else {
            result = words.stream()
                    .filter(word -> word.length() == maxLength)
                    .distinct()
                    .map(word -> new String[]{word,
                            String.valueOf(Collections.frequency(words, word))})
                    .collect(Collectors.toList());
        }
        return result;
    }

    //количество слов которые содеражат слово пользователя
    public static long countWordsContaining(List<String> words, String string) {
        return words.stream()
                .filter(word -> word.contains(string))
                .count();
    }
}
