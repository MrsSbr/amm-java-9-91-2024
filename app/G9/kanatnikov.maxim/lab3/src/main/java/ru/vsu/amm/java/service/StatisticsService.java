package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Statistics;
import ru.vsu.amm.java.enums.Name;
import ru.vsu.amm.java.enums.Result;

import java.time.LocalDate;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
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

    public static Set<Integer> yearWithBestPercentageOfAcquitted(List<Statistics> statistics) {
        if (statistics == null || statistics.isEmpty()) {
            return Set.of();
        }

        List<Integer> years = statistics.stream()
                .map(s -> s.date().getYear())
                .distinct()
                .toList();

        double maxPercentage = years.stream()
                .map(year -> {
                    long total = statistics.stream()
                            .filter(s -> s.date().getYear() == year)
                            .count();
                    long acquitted = statistics.stream()
                            .filter(s -> s.date().getYear() == year && s.result() == Result.ACQUITTED)
                            .count();
                    return total == 0 ? 0.0 : (double) acquitted / total;
                })
                .max(Comparator.comparingDouble(Double::doubleValue))
                .orElse(0.0);

        return years.stream()
                .filter(year -> {
                    long total = statistics.stream()
                            .filter(s -> s.date().getYear() == year)
                            .count();
                    long acquitted = statistics.stream()
                            .filter(s -> s.date().getYear() == year && s.result() == Result.ACQUITTED)
                            .count();
                    double percentage = total == 0 ? 0.0 : (double) acquitted / total;
                    return percentage == maxPercentage;
                })
                .collect(Collectors.toSet());
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
