package ru.vsu.amm.java;

import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        InputHandler inputHandler = new InputHandler();


        String text = inputHandler.getTextInput();
        String searchWord = inputHandler.getSearchWord();


        TextProcessor processor = new SimpleTextProcessor(text);


        Set<String> uniqueWords = processor.process(TextProcessingMode.UNIQUE_WORDS, null);
        System.out.println("Уникальные слова: " + uniqueWords);


        List<String> longestWords = processor.process(TextProcessingMode.LONGEST_WORDS, null);
        System.out.println("Самые длинные слова: " + longestWords);


        long count = processor.process(TextProcessingMode.WORDS_CONTAINING, searchWord);
        System.out.println("Количество слов, содержащих '" + searchWord + "': " + count);
    }
}
