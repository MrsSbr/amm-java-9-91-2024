package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.PlantLogEntry;
import ru.vsu.amm.java.enums.Fertilizer;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class PlantDiaryService {
    private static final double DEFAULT_WATERING_INTERVAL = 0.0;
    private static final int MINIMAL_WATERING_NUMBER = 2;

    public static Map<String, Double> getAverageWateringFrequency(List<PlantLogEntry> plants) {
        if (plants == null || plants.isEmpty()) {
            return Collections.emptyMap();
        }

        return plants.stream()
                .collect(Collectors.groupingBy(PlantLogEntry::name))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> calculateAverageWateringInterval(entry.getValue())
                ));
    }

    private static Double calculateAverageWateringInterval(List<PlantLogEntry> plantLogs) {
        List<PlantLogEntry> sortedByDateEntries = plantLogs.stream()
                .sorted(Comparator.comparing(PlantLogEntry::date))
                .toList();

        if (sortedByDateEntries.size() < MINIMAL_WATERING_NUMBER) {
            return DEFAULT_WATERING_INTERVAL;
        }

        List<Long> intervals = new ArrayList<>();
        for (int i = 1; i < sortedByDateEntries.size(); i++) {
            long distance = ChronoUnit.DAYS.between(
                    sortedByDateEntries.get(i - 1).date(),
                    sortedByDateEntries.get(i).date()
            );
            intervals.add(distance);
        }

        return intervals.stream()
                .mapToLong(Long::longValue)
                .average()
                .orElse(DEFAULT_WATERING_INTERVAL);
    }

    public static Map<Fertilizer, Set<String>> findPlantsByFertilizer(List<PlantLogEntry> plants) {
        if (plants == null || plants.isEmpty()) {
            return Collections.emptyMap();
        }

        return plants.stream()
                .collect(Collectors.groupingBy(PlantLogEntry::fertilizer,
                        Collectors.mapping(PlantLogEntry::name, Collectors.toSet())));
    }

    public static Optional<String> findMostWateredPlant(List<PlantLogEntry> plants) {
        if (plants == null || plants.isEmpty()) {
            return Optional.empty();
        }

        return plants.stream()
                .collect(Collectors.groupingBy(PlantLogEntry::name,
                        Collectors.summingDouble(PlantLogEntry::waterAmount)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }
}
