package ru.vsu.amm.java;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.vsu.amm.java.TextAnalyzer.countWordsContaining;
import static ru.vsu.amm.java.TextAnalyzer.getLongestWordsWithFrequency;
import static ru.vsu.amm.java.TextAnalyzer.getUniqueWords;

class TextAnalyzerTest {

    @Test
    @DisplayName("Тест на уникальные слова")
    public void testUniqueWords() {
        WordList wordList = new WordList("apple banana apple orange");
        List<String> expected = Arrays.asList("apple", "banana", "orange");
        assertEquals(expected, getUniqueWords(wordList.getWords()));
    }

    @Test
    @DisplayName("Тест на пустую строку из пробелов")
    public void testUniqueWordsWithEmptyString() {
        WordList wordList = new WordList("   ");
        List<String> expected = List.of();
        assertEquals(expected, getUniqueWords(wordList.getWords()));
    }

    @Test
    @DisplayName("Тест на самые длинные слова с количеством их в тексте")
    public void testLongestWordsWithFrequency() {
        WordList wordList = new WordList("apple banana cucumber   cucumber");
        List<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"cucumber", "2"});
        List<String[]> result = getLongestWordsWithFrequency(wordList.getWords());
        assertEquals(expected.size(), result.size());
        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    @DisplayName("Тест на самые длинные слова с количеством их в тексте, вариантов ответа больше 1")
    public void testLongestWordsWithFrequencyMoreOneWord() {
        WordList wordList = new WordList("apple banana orange banana orange ");
        List<String[]> expected = new ArrayList<>();
        expected.add(new String[]{"banana", "2"});
        expected.add(new String[]{"orange", "2"});
        List<String[]> result = getLongestWordsWithFrequency(wordList.getWords());
        assertEquals(expected.size(), result.size());
        assertArrayEquals(expected.toArray(), result.toArray());
    }

    @Test
    @DisplayName("Тест на количество слов содержащих заданное")
    public void testCountWordsContaining() {
        WordList wordList = new WordList("apple banana apple orange  app");
        assertEquals(3, countWordsContaining(wordList.getWords(), "app"));
    }
}