package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entites.PlantLog;
import ru.vsu.amm.java.enums.PlantType;

import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Optional;
import java.util.Collections;
import java.util.stream.Collectors;

public class PlantLogService {

    private static List<PlantLog> plantLogs;

    public PlantLogService(List<PlantLog> plantLogs) {
        this.plantLogs = plantLogs;
    }

    //  Месяц, в котором было продано больше всего цветущих растений
    public static Optional<Month> findMonthWithMostFlowers() {
        if (plantLogs.isEmpty()) {
            return Optional.empty();
        }

        return plantLogs.stream()
                .filter(plantLog -> plantLog.type() == PlantType.FLOWERING)
                .collect(Collectors.groupingBy(plantLog -> plantLog.date().getMonth(), Collectors.counting()))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);

    }

    //  Для каждого растения найти все размеры горшков, в которых оно продается
    public Map<String, Set<Integer>> findPotSizesPerPlant() {
        return plantLogs.stream()
                .collect(Collectors.groupingBy(PlantLog::Name,
                        Collectors.mapping(PlantLog::sizePot, Collectors.toSet())));
    }

    // Найти растение(растения), на продаже которого(-ых) магазин заработал меньше всего
    public Set<String> findPlantsWithLowestEarnings() {
        if (plantLogs.isEmpty()) {
            return Collections.emptySet();
        }

        Map<String, Integer> plantEarnings = plantLogs.stream()
                .collect(Collectors.groupingBy(PlantLog::Name,
                        Collectors.summingInt(PlantLog::price)));

        Integer minEarnings = plantEarnings.values().stream()
                .min(Integer::compareTo)
                .orElse(0);
        return plantEarnings.entrySet().stream()
                .filter(entry -> entry.getValue().equals(minEarnings))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
