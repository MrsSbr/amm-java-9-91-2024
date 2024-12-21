package ru.vsu.amm.java;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class GameService {

    private static final Logger logger = Logger.getLogger(GameService.class.getName());

    public static Genre getTopGenre(List<GameRecord> records) {
        logger.info("Calculating top genre by average rating");
        return records.stream()
                .collect(Collectors.groupingBy(GameRecord::getGenre, Collectors.averagingInt(GameRecord::getRating)))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public static Month getMonthWithMostHours(List<GameRecord> records) {
        logger.info("Calculating month with most hours spent");
        return records.stream()
                .collect(Collectors.groupingBy(
                        record -> record.getCompletionDate().getMonth(),
                        Collectors.summingInt(GameRecord::getHoursSpent)
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public static List<String> getRepeatedGames(List<GameRecord> records) {
        logger.info("Finding games completed more than once");
        return records.stream()
                .collect(Collectors.groupingBy(GameRecord::getTitle, Collectors.counting()))
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
