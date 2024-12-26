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
    private static final String DELIMITER = ";";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final int NAME_INDEX = 0;
    private static final int GENRE_INDEX = 1;
    private static final int DATE_INDEX = 2;
    private static final int SCORE_INDEX = 3;
    private static final int PLAY_TIME_INDEX = 4;


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
        String[] parts = line.split(DELIMITER);
        return new GameRecord(
                parts[NAME_INDEX],
                Genre.valueOf(parts[GENRE_INDEX].toUpperCase()),
                LocalDate.parse(parts[DATE_INDEX], DATE_FORMATTER),
                Integer.parseInt(parts[SCORE_INDEX]),
                Integer.parseInt(parts[PLAY_TIME_INDEX])
        );
    }
}