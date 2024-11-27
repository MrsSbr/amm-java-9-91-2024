package ru.vsu.amm.java.services;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

import java.util.logging.Logger;
import java.util.stream.Collectors;

public class DealsAnalyzer {
    private static final Logger logger = Logger.getLogger(DealsAnalyzer.class.getName());

    public static String findMostEffectiveManagerLastMonth(List<Deal> deals) {
        try {
            LocalDate oneMonthAgo = LocalDate.now().minusMonths(1);

            return deals.stream()
                    .filter(deal -> deal.date().isAfter(oneMonthAgo))
                    .collect(Collectors.groupingBy(Deal::manager, Collectors.summingDouble(Deal::amount)))
                    .entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse("No data");
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error finding the most effective manager", e);
            return "Error";
        }
    }

    public static Map<String, Double> collectIncomeByClients(List<Deal> deals) {
        try {
            return deals.stream()
                    .collect(Collectors.groupingBy(Deal::client, Collectors.summingDouble(Deal::amount)));
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error collecting income by clients", e);
            return Map.of();
        }
    }

    public static Month findMostProfitableMonth(List<Deal> deals) {
        try {
            LocalDate oneYearsAgo = LocalDate.now().minusYears(1);

            return deals.stream()
                    .filter(deal -> deal.date().isAfter(oneYearsAgo))
                    .collect(Collectors.groupingBy(deal -> deal.date().getMonth(), Collectors.summingDouble(Deal::amount)))
                    .entrySet().stream()
                    .max(Map.Entry.comparingByValue())
                    .map(Map.Entry::getKey)
                    .orElse(null);
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Error finding the most profitable month", e);
            return null;
        }
    }
}
