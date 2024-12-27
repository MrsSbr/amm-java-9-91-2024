package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.PlantRecord;
import ru.vsu.amm.java.enums.FertilizerBrand;
import ru.vsu.amm.java.enums.Plant;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PlantDiaryProcessor {
    private static final Logger logger = Logger.getLogger(PlantDiaryProcessor.class.getName());
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final int DATE_INDEX = 0;
    private static final int PLANT_INDEX = 1;
    private static final int WATER_AMOUNT_INDEX = 2;
    private static final int FERTILIZER_INDEX = 3;

    public static List<PlantRecord> readPlantRecords(String filePath) {
        try {
            return Files.lines(Paths.get(filePath))
                    .map(PlantDiaryProcessor::parseLine)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

        } catch (IOException e) {
           logger.log(Level.SEVERE, "Error reading file: {0}", e.getMessage());
           return new ArrayList<>();
        }
    }

    private static PlantRecord parseLine(String line) {
        try {
            String[] parts = line.split(";");
            if (parts.length < 3) {
                logger.log(Level.WARNING, "Invalid line in file: {0}", line);
                return null;
            }
            LocalDate date = LocalDate.parse(parts[DATE_INDEX].trim(), DATE_FORMATTER);
            Plant plantName = Plant.valueOf(parts[PLANT_INDEX].trim());
            int waterAmount = Integer.parseInt(parts[WATER_AMOUNT_INDEX].trim());
            FertilizerBrand fertilizerBrand = (parts.length > 3) ? FertilizerBrand.valueOf(parts[FERTILIZER_INDEX].trim()) : null;
            return new PlantRecord(date, plantName, waterAmount, fertilizerBrand);
        } catch (DateTimeException | NumberFormatException e) {
            logger.log(Level.WARNING, "Line {0} parsing error", line);
            return null;
        }
    }
}
