package ru.vsu.amm.java.services;

import ru.vsu.amm.java.classes.FeedingRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FeedingFileHandler {
    private static final Logger logger = Logger.getLogger(FeedingFileHandler.class.getName());

    public static List<FeedingRecord> readFeedingRecords(String filePath) {
        List<FeedingRecord> records = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            records = lines.map(line -> {
                String[] parts = line.split(";");
                LocalDate date = LocalDate.parse(parts[0], formatter);
                String animalName = parts[1];
                List<String> products = Arrays.asList(parts[2].split(","));
                double foodWeight = Double.parseDouble(parts[3]);
                return new FeedingRecord(date, animalName, products, foodWeight);
            }).collect(Collectors.toList());
        } catch (IOException e) {
            logger.severe("Error reading file: " + e.getMessage());
        }

        return records;
    }
}