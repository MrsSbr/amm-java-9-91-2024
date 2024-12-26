package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.HorseRacing;
import ru.vsu.amm.java.entity.HorseStatistics;
import ru.vsu.amm.java.service.HorseRacingService;

import java.util.List;
import java.util.Map;

public class HorseRacingApplication {
    private static final String PATH = "app/G9/mokshin.nikita/lab4/horseRacing.txt";

    public static void main(String[] args) {
        HorseRacingService.generateToFile(PATH, 30);

        List<HorseRacing> horseRacings = HorseRacingService.loadFromFile(PATH);

        System.out.printf("Most successful horse: %s\n\n", HorseRacingService.findMostSuccessfulHorse(horseRacings));
        System.out.printf("Most frequent horse: %s\n\n", HorseRacingService.findMostFrequentHorse(horseRacings));
        System.out.println("Horses statistics:");
        Map<String, HorseStatistics> horseStatistics = HorseRacingService.calculateHorseStatistics(horseRacings);
        for (Map.Entry<String, HorseStatistics> entry : horseStatistics.entrySet()) {
            System.out.printf("%s: %s\n", entry.getKey(), entry.getValue());
        }
    }
}