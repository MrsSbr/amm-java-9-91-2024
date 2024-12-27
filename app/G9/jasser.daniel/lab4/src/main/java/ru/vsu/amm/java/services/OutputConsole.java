package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entities.PastryRecord;

import java.time.Month;
import java.util.List;
import java.util.Map;

public class OutputConsole {
    public static void printMonthWithLowestIncome(Month month) {
        System.out.println("Month with the lowest income is " + month);
    }
    public static void printHeaviestPastryPerMonth(Map<Month, Integer> map) {
        for (Map.Entry<Month, Integer> entry : map.entrySet()) {
            Month month = entry.getKey();
            Integer count = entry.getValue();
            System.out.println("Month and the heaviest pastry ");
            System.out.println(month + " " + count);
        }
    }

    public static void printAllRecordsByMonth(Map<Month, List<PastryRecord>> map) {
        for (Map.Entry<Month, List<PastryRecord>> entry : map.entrySet()) {
            Month month = entry.getKey();
            List<PastryRecord> pastryRecords = entry.getValue();
            System.out.println(month);
            for (PastryRecord pastryRecord : pastryRecords) {
                System.out.println("Pastry: " + pastryRecord.getPastryName() + " " + pastryRecord.getCompletionDate());
            }
        }
    }
}
