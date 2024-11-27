package ru.vsu.amm.java;

import java.util.Arrays;
import java.util.List;

public class WordList {
    private final List<String> words;

    public WordList(String text) {
        words = Arrays.stream(text.split("\\s+")).toList();
    }


    public List<String> getWords() {
        return words;
    }
}