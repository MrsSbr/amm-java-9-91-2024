package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class TextAnalyzerTest {

    @Test
    public void testUniqueWords() {
        TextAnalyzer analyzer = new TextAnalyzer("apple banana apple orange");
        Set<String> expected = new HashSet<>(Arrays.asList("apple", "banana", "orange"));
        assertEquals(expected, analyzer.getUniqueWords());
    }

    @Test
    public void testLongestWordsWithFrequency() {
        TextAnalyzer analyzer = new TextAnalyzer("apple banana cucumber cucumber");
        List<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"cucumber", "2"});
        List<String[]> result=analyzer.getLongestWordsWithFrequency();
        assertEquals(expected.size(),result.size());
        assertArrayEquals(expected.toArray(),result.toArray());
    }

    @Test
    public void testCountWordsContaining() {
        TextAnalyzer analyzer = new TextAnalyzer("apple banana apple orange  app");
        assertEquals(3, analyzer.countWordsContaining("app"));
    }
}