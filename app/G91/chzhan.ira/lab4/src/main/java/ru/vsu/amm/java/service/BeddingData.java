package ru.vsu.amm.java.service;

import ru.vsu.amm.java.enums.*;
import ru.vsu.amm.java.entity.BeddingRecord;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
public class BeddingData {
    private static final Logger logger = Logger.getLogger(BeddingData.class.getName());
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<BeddingRecord> loadData(String filePath) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            return lines
                    .map(this::parseLine)
                    .filter(record -> record != null)
                    .toList();

        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error loading data", e.getMessage());
            return List.of();
        }
    }

    private static final int DATE_COLUMN = 0;
    private static final int NAME_COLUMN = 1;
    private static final int SIZE_COLUMN = 2;
    private static final int COLORS_COLUMN = 3;
    private static final int MATERIAL_COLUMN = 4;

    private BeddingRecord parseLine(String line) {
        String[] parts = line.split(" ");
        try {
            return new BeddingRecord(
                    LocalDate.parse(parts[DATE_COLUMN], DATE_FORMATTER),
                    parts[NAME_COLUMN],
                    Size.valueOf(parts[SIZE_COLUMN]),
                    Colors.valueOf(parts[COLORS_COLUMN]),
                    Material.valueOf(parts[MATERIAL_COLUMN])
            );
        } catch (DateTimeParseException | IllegalArgumentException e) {
            logger.log(Level.WARNING, "Error parsing line: {0}. Skipping.", line);
            return null;
        }
    }
}
