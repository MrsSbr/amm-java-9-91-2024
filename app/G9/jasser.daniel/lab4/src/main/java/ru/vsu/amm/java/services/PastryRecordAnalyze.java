package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entities.PastryRecord;

import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PastryRecordAnalyze {

    public Month findMonthWithLowestIncome(List<PastryRecord> pastryRecordList) {
        Map<Month, Integer> monthlyIncome = new HashMap<>();

        for (PastryRecord pastryRecord : pastryRecordList) {
            Month month = pastryRecord.getCompletionDate().getMonth();
            int cost = pastryRecord.getCost();

            monthlyIncome.put(month,
                    monthlyIncome.getOrDefault(month, 0) + cost);
        }

        return monthlyIncome.entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    public Map<Month, Integer> findHeaviestPastryPerMonth(List<PastryRecord> pastryRecordList) {
        Map<Month, Integer> heaviestPastry = new HashMap<>();

        for (PastryRecord pastryRecord : pastryRecordList) {
            Month month = pastryRecord.getCompletionDate().getMonth();
            int weight = pastryRecord.getWeight();

            heaviestPastry.put(month,
                    Math.max(heaviestPastry.getOrDefault(month, 0), weight)
            );
        }

        return heaviestPastry;
    }

    public Map<Month, List<PastryRecord>> findAllRecordsByMonth(List<PastryRecord> pastryRecordList) {
        Map<Month, List<PastryRecord>> recordsByMonth = new HashMap<>();

        for (PastryRecord pastryRecord : pastryRecordList) {
            Month month = pastryRecord.getCompletionDate().getMonth();
            List<PastryRecord> records = recordsByMonth.get(month);
            if (records == null) {
                records = new ArrayList<>();
                recordsByMonth.put(month, records);
            }
            records.add(pastryRecord);
        }

        return recordsByMonth;
    }

}
