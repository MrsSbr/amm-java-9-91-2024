package ru.vsu.amm.java;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FileHandler {
    private static final Logger logger = Logger.getLogger(FileHandler.class.getName());

    public static List<GameRecord> readRecordsFromFile(String filePath) {
        try (var lines = Files.lines(Path.of(filePath))) {
            return lines.map(FileHandler::fromString)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            logger.info(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static GameRecord fromString(String line) {
        String[] parts = line.split(";");
        return new GameRecord(
                parts[0],
                Genre.valueOf(parts[1].toUpperCase()),
                LocalDate.parse(parts[2], DateTimeFormatter.ofPattern("dd-MM-yyyy")),
                Integer.parseInt(parts[3]),
                Integer.parseInt(parts[4])
        );
    }
}