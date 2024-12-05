package ru.vsu.amm.java;

import java.util.Collections;
import java.util.List;

public final class TextAnalyzer {
    //уникальные слова
    public static List<String> getUniqueWords(List<WordCount> words) {
        return words.stream()
                .map(WordCount::word)
                .distinct()
                .toList();
    }

    //самые длинные слова и их количеситво
    public static List<WordCount> getLongestWordsWithFrequency(List<WordCount> words) {
        int maxLength = words.stream()
                .mapToInt(word -> word.word().length())
                .max()
                .orElse(0);

        if (maxLength == 0) {
            return Collections.emptyList();
        } else {
            return words.stream()
                    .filter(word -> word.word().length() == maxLength)
                    .map(word -> new WordCount(word.word(), word.count()))
                    .toList();
        }
    }

    //количество слов которые содеражат слово пользователя
    public static long countWordsContaining(List<WordCount> words, String string) {
        return words.stream()
                .filter(wordCount -> wordCount.word().contains(string))
                .mapToInt(WordCount::count)
                .sum();
    }
}
