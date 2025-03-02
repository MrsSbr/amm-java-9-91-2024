package ru.vsu.amm.java;

import java.util.List;
import java.util.Set;

public interface TextProcessor {
    Set<String> getUniqueWords();
    List<String> getLongestWordsFrequency();
    long countWordsContaining(String substring);

    <T> T process(TextProcessingMode mode, String parameter);
}
