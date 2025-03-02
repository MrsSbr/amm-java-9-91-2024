package ru.vsu.amm.java;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import java.util.List;

public class SimpleTextProcessorTest {

    @Test
    public void testGetUniqueWords() {
        SimpleTextProcessor processor = new SimpleTextProcessor("кот собака кот лиса");
        Set<String> uniqueWords = processor.getUniqueWords();
        assertEquals(Set.of("кот", "собака", "лиса"), uniqueWords);
    }

    @Test
    public void testGetAllUniqueWords() {
        SimpleTextProcessor processor = new SimpleTextProcessor("кот собака лиса жираф волк");
        Set<String> uniqueWords = processor.getUniqueWords();
        assertEquals(Set.of("кот", "собака", "лиса", "жираф", "волк"), uniqueWords);
    }

    @Test
    public void testGetAllUniqueRepeatedWords() {
        SimpleTextProcessor processor = new SimpleTextProcessor("кот кот кот кот кот");
        Set<String> uniqueWords = processor.getUniqueWords();
        assertEquals(Set.of("кот"), uniqueWords);
    }
    @Test
    public void testGetUniqueWordsWithMultipySeparator() {
        SimpleTextProcessor processor = new SimpleTextProcessor("   кот  собака     кот  лиса   ");
        Set<String> uniqueWords = processor.getUniqueWords();
        assertEquals(Set.of("кот", "собака", "лиса"), uniqueWords);
    }

    @Test
    public void testGetUniqueWordsInEmptyString() {
        SimpleTextProcessor processor = new SimpleTextProcessor("");
        Set<String> uniqueWords = processor.getUniqueWords();
        assertTrue(uniqueWords.isEmpty());
    }

    @Test
    public void testGetLongestWordsFrequency() {
        SimpleTextProcessor processor = new SimpleTextProcessor("длинное слово супер длинное слово");
        List<String> longestWords = processor.getLongestWordsFrequency();
        assertEquals(List.of("длинное"), longestWords);
    }

    @Test
    public void testGetLongestWordsFrequencyInEmptyString() {
        SimpleTextProcessor processor = new SimpleTextProcessor("");
        List<String> longestWords = processor.getLongestWordsFrequency();
        assertTrue(longestWords.isEmpty());
    }

    @Test
    public void testGetLongestWordsFrequencyWithMultiplyWords() {
        SimpleTextProcessor processor = new SimpleTextProcessor("длинное ооочень сууупер");
        List<String> longestWords = processor.getLongestWordsFrequency();
        assertEquals(List.of("длинное", "ооочень", "сууупер"), longestWords);
    }

    @Test
    public void testGetLongestWordsFrequencyWithMultiplySeparator() {
        SimpleTextProcessor processor = new SimpleTextProcessor("  длинное    слово  супер   длинное  слово    ");
        List<String> longestWords = processor.getLongestWordsFrequency();
        assertEquals(List.of("длинное"), longestWords);
    }

    @Test
    public void testCountWordsContaining() {
        SimpleTextProcessor processor = new SimpleTextProcessor("яблоко банан ананас абрикос");
        long count = processor.countWordsContaining("ан");
        assertEquals(2, count); // "банан", "ананас"
    }

    @Test
    public void testCountWordsContainingInEmptySting() {
        SimpleTextProcessor processor = new SimpleTextProcessor("");
        long count = processor.countWordsContaining("ан");
        assertEquals(0, count);
    }

    @Test
    public void testCountWordsContainingWithMultiplySeparator() {
        SimpleTextProcessor processor = new SimpleTextProcessor("  яблоко    банан  ананас  абрикос    ");
        long count = processor.countWordsContaining("ан");
        assertEquals(2, count);
    }
}