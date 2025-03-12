package ru.vsu.amm.java.services;

import ru.vsu.amm.java.classes.FeedingRecord;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class FeedingService {
    private static final Logger logger = Logger.getLogger(FeedingService.class.getName());

    public static String findAnimalWithMostFoodLastMonth(List<FeedingRecord> records) {
        logger.info("Find animal with most food last month");
        LocalDate now = LocalDate.now();
        LocalDate lastMonth = now.minusMonths(1);

        return records.stream()
                .filter(record -> record.getDate().getMonth().equals(lastMonth.getMonth()))
                .collect(Collectors.groupingBy(FeedingRecord::getAnimalName, Collectors.summingDouble(FeedingRecord::getFoodWeight)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No data");
    }

    public static String findMonthWithMostVariety(List<FeedingRecord> records) {
        logger.info("Find month with most variety");
        return records.stream()
                .collect(Collectors.groupingBy(record -> record.getDate().getMonth(),
                        Collectors.flatMapping(record -> record.getProducts().stream(), Collectors.toSet())))
                .entrySet().stream()
                .max(Comparator.comparingInt(entry -> entry.getValue().size()))
                .map(entry -> entry.getKey().toString())
                .orElse("No data");
    }

    public static Map<String, List<String>> findProductsNotRepeated(List<FeedingRecord> records) {
        logger.info("Find products not repeated");
        LocalDate now = LocalDate.now();
        LocalDate lastMonth = now.minusMonths(1);

        Map<String, Set<String>> productsThisMonth = getProductsByMonth(records, now);
        Map<String, Set<String>> productsLastMonth = getProductsByMonth(records, lastMonth);

        return productsLastMonth.entrySet().stream()
                .filter(entry -> productsThisMonth.containsKey(entry.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .filter(product -> !productsThisMonth.get(entry.getKey()).contains(product))
                                .toList()));
    }

    private static Map<String, Set<String>> getProductsByMonth(List<FeedingRecord> records, LocalDate date) {
        return records.stream()
                .filter(record -> record.getDate().getMonth().equals(date.getMonth()))
                .collect(Collectors.groupingBy(FeedingRecord::getAnimalName,
                        Collectors.flatMapping(record -> record.getProducts().stream(), Collectors.toSet())));
    }
}