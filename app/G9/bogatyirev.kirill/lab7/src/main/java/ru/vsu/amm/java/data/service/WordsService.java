package ru.vsu.amm.java.data.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;


public class WordsService {

    private final String DEFAULT_FILE_PATH = "webapp/WEB-INF/properties/words";

    private static final Logger log = Logger.getLogger(WordsService.class.getName());

    public List<String> getRandomWords(int count) {
        try {
            List<String> words = Files.readAllLines(Paths.get(DEFAULT_FILE_PATH));

            Collections.shuffle(words);
            log.info("Слова сгенерировались");
            return words.subList(0, count);
        } catch (IOException e) {
            log.info("Ошибка генерации слов");
            throw new RuntimeException("WordsService exception",e);
        }
    }
}
