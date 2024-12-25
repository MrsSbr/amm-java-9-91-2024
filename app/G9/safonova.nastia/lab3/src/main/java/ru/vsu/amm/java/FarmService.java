package ru.vsu.amm.java;

import java.time.Month;
import java.time.temporal.IsoFields;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FarmService {

    private static final int ID_WEEK = 100;

    public static Month findBestMonthOrNull(List<DayStatistic> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        day -> day.date().getMonth(),
                        Collectors.summarizingDouble(day -> day.feedConsumed() / day.milkProduced())
                ))
                .entrySet().stream()
                .min(Comparator.comparingDouble(entry -> entry.getValue().getAverage()))
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public static double averageMilkPerWeek(List<DayStatistic> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        day -> day.date().getYear() * ID_WEEK + day.date().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR),
                        Collectors.summingDouble(DayStatistic::milkProduced)
                ))
                .values().stream()
                .mapToDouble(Double::doubleValue)
                .average()
                .orElse(0.0);
    }

    public static double totalFeedConsumed(List<DayStatistic> records) {
        return records.stream()
                .mapToDouble(DayStatistic::feedConsumed)
                .sum();
    }
}


