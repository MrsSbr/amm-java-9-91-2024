package ru.vsu.amm.java;

import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Comparator;
import java.util.HashMap;
import java.util.stream.Collectors;

public class Statistic {

    public static List<String> mostSuccessManager(List<Deal> deals) {
        if (deals == null || deals.isEmpty())
            return new ArrayList<>();
        Map<String, Double> managers = deals.stream()
                .collect(Collectors.groupingBy(Deal::getManager, Collectors.summingDouble(Deal::getPrice)));
        Double max = managers.values().stream().max(Comparator.comparingDouble(Double::doubleValue)).orElse(0D);
        return managers.entrySet().stream()
                .filter(entry -> Math.abs(entry.getValue() - max) < 0.01D)
                .map(Map.Entry::getKey)
                .toList();
    }

    public static Map<String, Double> incomeFromClients(List<Deal> deals) {
        if (deals == null || deals.isEmpty())
            return new HashMap<>();
        return deals.stream()
                .collect(Collectors.groupingBy(Deal::getClient, Collectors.summingDouble(Deal::getPrice)));
    }

    public static List<Month> mostSuccessMonth(List<Deal> deals) {
        if (deals == null || deals.isEmpty())
            return new ArrayList<>();
        Map<Month, Double> months = deals.stream()
                .collect(Collectors.groupingBy(deal -> deal.getDate().getMonth()
                        , Collectors.summingDouble(Deal::getPrice)));
        Double max = months.values().stream().max(Comparator.comparingDouble(Double::doubleValue)).orElse(0D);
        return months.entrySet().stream()
                .filter(entry -> Math.abs(entry.getValue() - max) < 0.01D)
                .map(Map.Entry::getKey)
                .toList();
    }
}
