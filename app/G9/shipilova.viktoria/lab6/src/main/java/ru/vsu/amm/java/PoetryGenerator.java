package ru.vsu.amm.java;

import ru.vsu.amm.java.poetry.Poet;
import ru.vsu.amm.java.poetry.Poem;

import java.util.concurrent.Executors;
import java.util.logging.Logger;

public class PoetryGenerator {
    private static final Logger log = Logger.getLogger(PoetryGenerator.class.getName());

    public static void main(String[] args) {
        log.info("Поэты начали писать...");
        Poem poem = new Poem();
        String[] poets = {
                "app/G9/shipilova.viktoria/lab6/src/main/java/ru/vsu/amm/java/poetry/Pushkin.txt",
                "app/G9/shipilova.viktoria/lab6/src/main/java/ru/vsu/amm/java/poetry/Yesenin.txt",
                "app/G9/shipilova.viktoria/lab6/src/main/java/ru/vsu/amm/java/poetry/Shnur.txt",
                "app/G9/shipilova.viktoria/lab6/src/main/java/ru/vsu/amm/java/poetry/Tsvetaeva.txt"};

        String[] poetNames = {"Pushkin", "Yesenin", "Shnur", "Tsvetaeva"};

        try (var executorService = Executors.newFixedThreadPool(poets.length)) {
            for (int i = 0; i < poets.length; i++) {
                executorService.submit(new Poet(poets[i], poem));
                log.info("Запущен поток для поэта " + poetNames[i] + " (файл: " + poets[i] + ")");
            }
        }

        poem.printPoem();

        String outputFileName = "app/G9/shipilova.viktoria/lab6/result_poetry.txt";
        poem.savePoem(outputFileName);
        log.info("Стихотворение сохранено в файл: " + outputFileName);
    }
}
