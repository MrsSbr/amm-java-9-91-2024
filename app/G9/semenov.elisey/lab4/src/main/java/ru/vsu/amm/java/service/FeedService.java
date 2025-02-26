package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.FeedRecord;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.Set;
public class FeedService {
    private static final Logger logger = Logger.getLogger(FeedService.class.getName());

    public String findMostEatingAnimal(List<FeedRecord> records) {
        LocalDate lastMonthStart = LocalDate.now().minusMonths(1).withDayOfMonth(1);

        return records.stream()
                .filter(r -> r.getDate().isAfter(lastMonthStart))
                .collect(Collectors.groupingBy(
                        FeedRecord::getName,
                        Collectors.summingDouble(FeedRecord::getWeight)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("РќРµС‚ РґР°РЅРЅС‹С…");
    }

    public String findMostDiverseProductsMonth(List<FeedRecord> records) {
        return records.stream()
                .collect(Collectors.groupingBy(
                        r -> r.getDate().getYear() + "-" + r.getDate().getMonthValue(),
                        Collectors.flatMapping(r -> r.getProducts().stream().distinct(), Collectors.toSet())))
                .entrySet().stream()
                .max(Comparator.comparingInt(e -> e.getValue().size()))
                .map(Map.Entry::getKey)
                .orElse("РќРµС‚ РґР°РЅРЅС‹С…");
    }

    public static Map<String, List<String>> findMissingProducts(List<FeedRecord> records) {
        LocalDate now = LocalDate.now();
        LocalDate thisMonthStart = now.withDayOfMonth(1);
        LocalDate lastMonthStart = thisMonthStart.minusMonths(1);

        Map<String, Set<String>> lastMonthProducts = records.stream()
                .filter(r -> r.getDate().isAfter(lastMonthStart) && r.getDate().isBefore(thisMonthStart))
                .collect(Collectors.groupingBy(
                        FeedRecord::getName,
                        Collectors.flatMapping(r -> r.getProducts().stream(), Collectors.toSet())));

        Map<String, Set<String>> thisMonthProducts = records.stream()
                .filter(r -> r.getDate().isAfter(thisMonthStart))
                .collect(Collectors.groupingBy(
                        FeedRecord::getName,
                        Collectors.flatMapping(r -> r.getProducts().stream(), Collectors.toSet())));

        return lastMonthProducts.entrySet().stream()
                .filter(entry -> thisMonthProducts.containsKey(entry.getKey()))
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .filter(product -> !thisMonthProducts.get(entry.getKey()).contains(product))
                                .toList()));
    }
}