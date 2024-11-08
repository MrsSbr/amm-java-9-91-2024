package ru.vsu.amm.java;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TextAnalyzerTest {

    @Test
    @DisplayName("Тест на уникальные слова")
    public void testUniqueWords() {
        TextAnalyzer analyzer = new TextAnalyzer("apple banana apple orange");
        List<String> expected = Arrays.asList("apple", "banana", "orange");
        assertEquals(expected, analyzer.getUniqueWords());
    }

    @Test
    @DisplayName("Тест на пустую строку из пробелов")
    public void testUniqueWordsWithEmptyString() {
        TextAnalyzer analyzer = new TextAnalyzer("   ");
        List<String> expected = List.of();
        assertEquals(expected, analyzer.getUniqueWords());
    }

    @Test
    @DisplayName("Тест на самые длинные слова с количеством их в тексте")
    public void testLongestWordsWithFrequency() {
        TextAnalyzer analyzer = new TextAnalyzer("apple banana cucumber   cucumber");
        List<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"cucumber", "2"});
        List<String[]> result = analyzer.getLongestWordsWithFrequency();
        assertEquals(expected.size(), result.size());
        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    @DisplayName("Тест на количество слов содержащих заданное")
    public void testCountWordsContaining() {
        TextAnalyzer analyzer = new TextAnalyzer("apple banana apple orange  app");
        assertEquals(3, analyzer.countWordsContaining("app"));
    }
}