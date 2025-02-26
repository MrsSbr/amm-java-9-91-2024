package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.FeedRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;


public class FileService {
    private static final Logger logger = Logger.getLogger(FileService.class.getName());

    public static List<FeedRecord> readFeedRecords(String filePath) {
        logger.info("call readFeedRecords");
        List<FeedRecord> records = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");

        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            records = lines.map(line -> {
                String[] parts = line.split(";");
                LocalDate date = LocalDate.parse(parts[0], formatter);
                String name = parts[1];
                List<String> products = Arrays.asList(parts[2].split(","));
                double foodWeight = Double.parseDouble(parts[3]);
                return new FeedRecord(date, name, products, foodWeight);
            }).toList();
        } catch (IOException e) {
            logger.severe("error:" + e.getMessage());
        }

        return records;
    }
}
