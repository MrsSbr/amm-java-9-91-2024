package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Statistics;
import ru.vsu.amm.java.enums.Name;
import ru.vsu.amm.java.enums.Result;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticsService {
    private StatisticsService() {
    }

    public static Set<Name> getPeopleConvictedMoreThanOnceLastThreeYears(List<Statistics> statistics) {
        if (statistics == null || statistics.isEmpty()) {
            return Set.of();
        }

        LocalDate threeYearsAgo = LocalDate.now().minusYears(3);
        return statistics.stream()
                .filter(s -> s.date().isAfter(threeYearsAgo) && s.result() == Result.CONVICTED)
                .collect(Collectors.groupingBy(Statistics::defendantName, Collectors.counting()))
                .entrySet().stream()
                .filter(e -> e.getValue() > 1)
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }

    public static int yearWithBestPercentageOfAcquitted(List<Statistics> statistics) {
        if (statistics == null || statistics.isEmpty()) {
            return 0;
        }

        return statistics.stream()
                .collect(Collectors.groupingBy(s -> s.date().getYear()))
                .entrySet().stream()
                .map(e -> {
                    int total = e.getValue().size();
                    long acquitted = e.getValue().stream()
                            .filter(s -> s.result() == Result.ACQUITTED)
                            .count();
                    double percentage = total == 0 ? 0 : (double) acquitted / total;
                    return new AbstractMap.SimpleEntry<>(e.getKey(), percentage);
                })
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .map(Map.Entry::getKey)
                .orElse(0);
    }

    public static Set<Name> getPeopleAsPlaintiffsAndDefendants(List<Statistics> statistics) {
        if (statistics == null || statistics.isEmpty()) {
            return Set.of();
        }

        Set<Name> plaintiffs = statistics.stream()
                .map(Statistics::plaintiffName)
                .collect(Collectors.toSet());

        Set<Name> defendants = statistics.stream()
                .map(Statistics::defendantName)
                .collect(Collectors.toSet());

        defendants.retainAll(plaintiffs);

        return defendants;
    }
}
