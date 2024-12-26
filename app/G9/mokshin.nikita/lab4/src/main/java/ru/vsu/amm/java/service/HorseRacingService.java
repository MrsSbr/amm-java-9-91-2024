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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HorseRacingService {
    private static final Logger logger = Logger.getLogger(HorseRacingService.class.getName());

    public static List<HorseRacing> loadFromFile(String path) {
        logger.log(Level.INFO, "Loading data from file: {0}", path);
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            List<HorseRacing> races = reader.lines()
                    .map(line -> {
                        String[] parts = line.split(";");
                        LocalDate raceDate = LocalDate.parse(parts[0]);
                        String firstPlaceNameHorse = parts[1];
                        String secondPlaceNameHorse = parts[2];
                        String thirdPlaceNameHorse = parts[3];
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
            throw new RuntimeException(e);
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
        Map<String, Integer> horseParticipationCount = new HashMap<>();
        for (HorseRacing race : races) {
            horseParticipationCount.merge(race.getFirstPlaceNameHorse(), 1, Integer::sum);
            horseParticipationCount.merge(race.getSecondPlaceNameHorse(), 1, Integer::sum);
            horseParticipationCount.merge(race.getThirdPlaceNameHorse(), 1, Integer::sum);
        }
        String result = findHorseNameMaxCount(horseParticipationCount);
        logger.log(Level.INFO, "The most frequently participating horse is: {0}", result);
        return result;
    }

    public static String findMostSuccessfulHorse(List<HorseRacing> races) {
        logger.log(Level.INFO, "Finding the most successful horse");
        Map<String, Integer> horseSuccessCount = new HashMap<>();

        for (HorseRacing race : races) {
            horseSuccessCount.merge(race.getFirstPlaceNameHorse(), 3, Integer::sum);
            horseSuccessCount.merge(race.getSecondPlaceNameHorse(), 2, Integer::sum);
            horseSuccessCount.merge(race.getThirdPlaceNameHorse(), 1, Integer::sum);
        }
        String result = findHorseNameMaxCount(horseSuccessCount);
        logger.log(Level.INFO, "The most successful horse is: {0}", result);
        return result;
    }

    public static Map<String, HorseStatistics> calculateHorseStatistics(List<HorseRacing> horseRacings) {
        logger.log(Level.INFO, "Calculating statistics for horses");
        Map<String, HorseStatistics> horseStats = new HashMap<>();

        for (HorseRacing race : horseRacings) {
            if(horseStats.containsKey(race.getFirstPlaceNameHorse())) {
                horseStats.get(race.getFirstPlaceNameHorse()).addFirstPlace();
            } else {
                HorseStatistics horseStatistics = new HorseStatistics();
                horseStatistics.addFirstPlace();
                horseStats.put(race.getFirstPlaceNameHorse(), horseStatistics);
            }

            if(horseStats.containsKey(race.getSecondPlaceNameHorse())) {
                horseStats.get(race.getSecondPlaceNameHorse()).addSecondPlace();
            } else {
                HorseStatistics horseStatistics = new HorseStatistics();
                horseStatistics.addSecondPlace();
                horseStats.put(race.getSecondPlaceNameHorse(), horseStatistics);
            }

            if(horseStats.containsKey(race.getThirdPlaceNameHorse())) {
                horseStats.get(race.getThirdPlaceNameHorse()).addThirdPlace();
            } else {
                HorseStatistics horseStatistics = new HorseStatistics();
                horseStatistics.addThirdPlace();
                horseStats.put(race.getThirdPlaceNameHorse(), horseStatistics);
            }
        }

        logger.log(Level.INFO, "Successfully calculated statistics for {0} horses", horseStats.size());
        return horseStats;
    }

    private static String findHorseNameMaxCount(Map<String, Integer> hourseMap) {
        String maxCountNameHorse = null;
        int maxCount = 0;

        for (Map.Entry<String, Integer> entry : hourseMap.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCountNameHorse = entry.getKey();
                maxCount = entry.getValue();
            }
        }
        return maxCountNameHorse;
    }

}
