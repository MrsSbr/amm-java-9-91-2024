package ru.vsu.amm.java.services;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DealsAnalyzer {
    public static String findMostEffectiveManagerLastMonth(List<Deal> deals) {
        LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);

        String topManager = deals.stream()
                .filter(deal -> deal.getDate().isAfter(oneMonthAgo))
                .collect(Collectors.groupingBy(Deal::getManager, Collectors.summingDouble(Deal::getAmount)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("No data");
        return topManager;
    }

    public static Map<String, Double> collectIncomeByClients(List<Deal> deals) {
        Map<String, Double> incomeByClients = deals.stream()
                .collect(Collectors.groupingBy(Deal::getClient, Collectors.summingDouble(Deal::getAmount)));

        return incomeByClients;
    }

    public static Month findMostProfitableMonth(List<Deal> deals) {
        LocalDate oneYearsAgo = LocalDate.now().minusYears(1);

        Month mostProfitableMonth = deals.stream()
                .filter(deal -> deal.getDate().isAfter(oneYearsAgo))
                .collect(Collectors.groupingBy(deal -> deal.getDate().getMonth(), Collectors.summingDouble(Deal::getAmount)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);

        return mostProfitableMonth;
    }
}
