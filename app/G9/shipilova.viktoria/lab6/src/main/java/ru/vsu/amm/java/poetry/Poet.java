package ru.vsu.amm.java.poetry;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class Poet implements Runnable {
    private static final Logger log = Logger.getLogger(Poet.class.getName());
    private String fileName;
    private Poem poem;

    public Poet(String fileName, Poem poem) {
        this.fileName = fileName;
        this.poem = poem;
    }

    @Override
    public void run() {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName, Charset.forName("Windows-1251")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
            log.info("Прочитаны строки из файла: " + fileName);
        } catch (IOException e) {
            log.log(Level.SEVERE, "Ошибка при чтении файла " + fileName, e);
            return;
        }

        for (String line : lines) {
            try {
                Thread.sleep(ThreadLocalRandom.current().nextInt(50, 200));
                poem.addLine(line);
                log.info("Добавлена строка: " + line);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                log.log(Level.WARNING, "Поток " + Thread.currentThread().getName() + " был прерван.", e);
            }
        }
    }
}
