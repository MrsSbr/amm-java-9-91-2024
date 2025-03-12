package ru.vsu.amm.java.service;

import ru.vsu.amm.java.enums.DrinkName;
import ru.vsu.amm.java.records.DrinkRecord;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static ru.vsu.amm.java.generators.BaristaGenerator.generateRandomDrinkRecord;
import static ru.vsu.amm.java.service.Logg.logger;

public class FileWorker {

    public void saveToFile(String path, int n) {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path))) {
            for (int i = 0; i < n; i++) {
                DrinkRecord drinkRecord = generateRandomDrinkRecord();
                writer.write(String.format("%s;%s;%s%n",
                                drinkRecord.getDrinkName(),
                                drinkRecord.getDate(),
                                drinkRecord.getTime()
                        )
                );
            };

            logger.fine("Saving file");
        } catch (IOException e) {
            logger.throwing(FileWorker.class.getName(), "saveToFile", e);

            System.out.println("Sorry, file hasn't saved:(");
        }
    }

    public List<DrinkRecord> loadFromFile(String path) {
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            logger.fine("loading file");

            final int FIRST_PART = 0;
            final int SECOND_PART = 1;
            final int THIRD_PART = 2;

            return reader.lines()
                    .map(line -> {
                        String[] parts = line.split(";");
                        String drinkName = parts[FIRST_PART];
                        LocalDate date = LocalDate.parse(parts[SECOND_PART]);
                        LocalTime time = LocalTime.parse(parts[THIRD_PART]);
                        return new DrinkRecord(DrinkName.valueOf(drinkName), date, time);
                    }).toList();
        } catch (IOException e) {
            logger.throwing(FileWorker.class.getName(), "loadFromFile", e);

            System.out.println("Sorry, file hasn't opened:(");
            return Collections.emptyList();
        }
    }
}
