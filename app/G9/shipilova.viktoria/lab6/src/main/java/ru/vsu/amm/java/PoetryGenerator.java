package ru.vsu.amm.java;

import ru.vsu.amm.java.poetry.Poet;
import ru.vsu.amm.java.poetry.Poem;

import java.util.logging.Level;
import java.util.logging.Logger;

public class PoetryGenerator {
    private static final Logger log = Logger.getLogger(PoetryGenerator.class.getName());

    public static void main(String[] args) {
        log.info("Поэты начали писать...");
        Poem poem = new Poem();
        String[] poetNames = {"Pushkin", "Yesenin", "Shnur", "Tsvetaeva"};
        String[] poets = {
                "app/G9/shipilova.viktoria/lab6/src/main/java/ru/vsu/amm/java/poetry/Pushkin.txt",
                "app/G9/shipilova.viktoria/lab6/src/main/java/ru/vsu/amm/java/poetry/Yesenin.txt",
                "app/G9/shipilova.viktoria/lab6/src/main/java/ru/vsu/amm/java/poetry/Shnur.txt",
                "app/G9/shipilova.viktoria/lab6/src/main/java/ru/vsu/amm/java/poetry/Tsvetaeva.txt"};

        Thread[] threads = new Thread[poets.length];
        for (int i = 0; i < poets.length; i++) {
            threads[i] = new Thread(new Poet(poets[i], poem));
            threads[i].setName(poetNames[i]);
            threads[i].start();
            log.info("Запущен поток для поэта " + threads[i].getName() + " (файл: " + poets[i] + ")");
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.log(Level.WARNING, "Поток main был прерван.", e);
            }
        }

        poem.printPoem();

        String outputFileName = "app/G9/shipilova.viktoria/lab6/result_poetry.txt";
        poem.savePoem(outputFileName);
        log.info("Стихотворение сохранено в файл: " + outputFileName);
    }
}
