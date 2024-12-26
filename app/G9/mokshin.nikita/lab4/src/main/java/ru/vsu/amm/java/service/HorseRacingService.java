package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.HorseRacing;
import ru.vsu.amm.java.entity.HorseStatistics;
import ru.vsu.amm.java.util.HorseRacingFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class HorseRacingService {
    private static final Logger logger = Logger.getLogger(HorseRacingService.class.getName());
    private static final int INDEX_DATE = 0;
    private static final int INDEX_FIRST_PLACE = 1;
    private static final int  INDEX_SECOND_PLACE = 2;
    private static final int INDEX_THIRD_PLACE = 3;

    public static List<HorseRacing> loadFromFile(String path) {
        logger.log(Level.INFO, "Loading data from file: {0}", path);
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            List<HorseRacing> races = reader.lines()
                    .map(line -> {
                        String[] parts = line.split(";");
                        LocalDate raceDate = LocalDate.parse(parts[INDEX_DATE]);
                        String firstPlaceNameHorse = parts[INDEX_FIRST_PLACE];
                        String secondPlaceNameHorse = parts[INDEX_SECOND_PLACE];
                        String thirdPlaceNameHorse = parts[INDEX_THIRD_PLACE];
                        return new HorseRacing(
                                raceDate,
                                firstPlaceNameHorse,
                                secondPlaceNameHorse,
                                thirdPlaceNameHorse);
                    }).toList();
            logger.log(Level.INFO, "Successfully loaded {0} races from file", races.size());
            return races;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to load data from file: {0}", path);
            return new ArrayList<>();
        }
    }

    public static void generateToFile(String path, int n) {
        logger.log(Level.INFO, "Generating races to file: {0}", path);
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(path))) {
            for (int i = 0; i < n; i++) {
                HorseRacing horseRacing = HorseRacingFactory.generate();
                writer.write(String.format("%s;%s;%s;%s\n",
                        horseRacing.getDate(),
                        horseRacing.getFirstPlaceNameHorse(),
                        horseRacing.getSecondPlaceNameHorse(),
                        horseRacing.getThirdPlaceNameHorse()
                ));
            }
            logger.log(Level.INFO, "Successfully generated {0} races to file: {1}", new Object[]{n, path});
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Failed to generate data to file: {0}", path);
            throw new RuntimeException(e);
        }
    }

    public static String findMostFrequentHorse(List<HorseRacing> races) {
        logger.log(Level.INFO, "Finding the most frequently participating horse");
        Map<String, Integer> horseParticipationCount = races.stream()
                .flatMap(race -> Stream.of(
                        race.getFirstPlaceNameHorse(),
                        race.getSecondPlaceNameHorse(),
                        race.getThirdPlaceNameHorse()
                ))
                .collect(Collectors.toMap(
                        horseName -> horseName,
                        horseName -> 1,
                        Integer::sum
                ));
        String result = findHorseNameMaxCount(horseParticipationCount);
        logger.log(Level.INFO, "The most frequently participating horse is: {0}", result);
        return result;
    }

    public static String findMostSuccessfulHorse(List<HorseRacing> races) {
        logger.log(Level.INFO, "Finding the most successful horse");
        Map<String, Integer> horseSuccessCount = races.stream()
                .flatMap(race -> Stream.of(
                        Map.entry(race.getFirstPlaceNameHorse(), 3),
                        Map.entry(race.getSecondPlaceNameHorse(), 2),
                        Map.entry(race.getThirdPlaceNameHorse(), 1)
                ))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        Integer::sum
                ));

        String result = findHorseNameMaxCount(horseSuccessCount);
        logger.log(Level.INFO, "The most successful horse is: {0}", result);
        return result;
    }

    public static Map<String, HorseStatistics> calculateHorseStatistics(List<HorseRacing> horseRacings) {
        logger.log(Level.INFO, "Calculating statistics for horses");
        Map<String, HorseStatistics> horseStats = horseRacings.stream()
                .flatMap(race -> Stream.of(
                        Map.entry(race.getFirstPlaceNameHorse(), 1),
                        Map.entry(race.getSecondPlaceNameHorse(), 2),
                        Map.entry(race.getThirdPlaceNameHorse(), 3)
                ))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            HorseStatistics stats = new HorseStatistics();
                            switch (entry.getValue()) {
                                case 1:
                                    stats.addFirstPlace();
                                    break;
                                case 2:
                                    stats.addSecondPlace();
                                    break;
                                case 3:
                                    stats.addThirdPlace();
                                    break;
                            }
                            return stats;
                        },
                        (existing, replacement) -> {
                            if (replacement.getFirstPlaces() > 0) {
                                existing.addFirstPlace();
                            }
                            if (replacement.getSecondPlaces() > 0) {
                                existing.addSecondPlace();
                            }
                            if (replacement.getThirdPlaces() > 0) {
                                existing.addThirdPlace();
                            }
                            return existing;
                        }
                ));

        logger.log(Level.INFO, "Successfully calculated statistics for {0} horses", horseStats.size());
        return horseStats;
    }

    private static String findHorseNameMaxCount(Map<String, Integer> horseMap) {
        return horseMap.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

}
