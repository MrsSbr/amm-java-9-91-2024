package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.PlantLogEntry;
import ru.vsu.amm.java.enums.Fertilizer;

import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class PlantDiaryService {

    public static Map<String, Double> getAverageWateringFrequency(List<PlantLogEntry> plants) {
        if (plants == null || plants.isEmpty()) {
            return Collections.emptyMap();
        }

        return plants.stream()
                .collect(Collectors.groupingBy(PlantLogEntry::name))
                .entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> {
                            List<PlantLogEntry> sortedByDateEntries = entry.getValue().stream()
                                    .sorted(Comparator.comparing(PlantLogEntry::date))
                                    .toList();

                            if (sortedByDateEntries.size() < 2) {
                                return 0.0;
                            }

                            List<Long> intervals = new ArrayList<>();
                            for (int i = 1; i < sortedByDateEntries.size(); i++) {
                                long distance = ChronoUnit.DAYS.between(sortedByDateEntries.get(i - 1).date(),
                                        sortedByDateEntries.get(i).date());
                                intervals.add(distance);
                            }

                            return intervals.stream()
                                    .mapToLong(Long::longValue)
                                    .average()
                                    .orElse(0.0);
                        }

                ));
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
