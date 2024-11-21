package ru.vsu.amm.java.services;

import ru.vsu.amm.java.enums.CoffeeType;
import ru.vsu.amm.java.records.CoffeeRecord;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class CoffeeService {
    public static final int FROM_HOURS = 7;
    public static final int TO_HOURS = 12;

    private static boolean isWeekDay(LocalDateTime date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return !dayOfWeek.equals(DayOfWeek.SUNDAY) && !dayOfWeek.equals(DayOfWeek.SATURDAY);
    }

    private static boolean checkValidHours(LocalDateTime date, int from, int to) {
        return date.getHour() >= from && date.getHour() <= to;
    }

    public static Map<CoffeeType, Float> calculateAveragePreparationTime(List<CoffeeRecord> records) {
        if (records == null || records.isEmpty()) {
            return Map.of();
        }

        return records.stream()
                .collect(Collectors.groupingBy(CoffeeRecord::name,
                        Collectors.collectingAndThen(
                                Collectors.averagingDouble(CoffeeRecord::preparationTime),
                                Double::floatValue
                        )
                ));
    }

    public static Optional<Integer> findBusiestHour(List<CoffeeRecord> records) {
        if (records == null || records.isEmpty()) {
            return Optional.empty();
        }

        return records.stream()
                .filter(coffeeRecord -> isWeekDay(coffeeRecord.date()))
                .collect(Collectors.groupingBy(
                        record -> record.date().getHour(),
                        Collectors.counting()
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey);
    }

    public static List<CoffeeType> findCoffeeOrderedFrom7To12(List<CoffeeRecord> records) {
        if (records == null || records.isEmpty()) {
            return List.of();
        }

        Map<CoffeeType, Long> drinks = records.stream()
                .filter(coffeeRecord -> checkValidHours(coffeeRecord.date(), FROM_HOURS, TO_HOURS))
                .collect(Collectors.groupingBy(CoffeeRecord::name, Collectors.counting()));

        long maxDrinks = drinks.values().stream()
                .max(Long::compareTo)
                .orElse(0L);

        if (maxDrinks == 0L) {
            return List.of();
        }

        return drinks.entrySet().stream()
                .filter(entry -> entry.getValue().equals(maxDrinks))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public static Optional<CoffeeType> findBestCoffeeRatio(List<CoffeeRecord> records) {
        if (records == null || records.isEmpty()) {
            return Optional.empty();
        }

        try {
            return records.stream()
                    .min(Comparator.comparingDouble(CoffeeRecord::getPriceToTimeRatio))
                    .map(CoffeeRecord::name);

        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
