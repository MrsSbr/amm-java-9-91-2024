package ru.vsu.amm.java.service;

import ru.vsu.amm.java.classes.BalmerPeakRecord;
import ru.vsu.amm.java.enums.AlcoholType;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BalmerPeakService {
    private static final int TOTAL_RECORDS = 2312;
    private static final int MIN_AMOUNT = 50;
    private static final int MAX_AMOUNT = 1000;

    public static List<BalmerPeakRecord> generateRecords() {
        List<BalmerPeakRecord> records = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < TOTAL_RECORDS; i++) {
            AlcoholType[] types = AlcoholType.values();
            records.add(
                new BalmerPeakRecord(types[rand.nextInt(types.length)],
                rand.nextInt(MIN_AMOUNT, MAX_AMOUNT),
                rand.nextBoolean())
            );
        }
        return records;
    }

    public static double getAverageAmountForPeak(List<BalmerPeakRecord> records) {
        if (records == null || records.isEmpty()) {
            return 0;
        }
        int totalAmount = records.stream()
                .filter(BalmerPeakRecord::hasBalmerPeak)
                .mapToInt(BalmerPeakRecord::amount)
                .sum();
        long countPeaks = records.stream().filter(BalmerPeakRecord::hasBalmerPeak).count();
        return countPeaks > 0 ? (double) totalAmount / countPeaks : 0;
    }

    public static List<AlcoholType> getUniqueAlcoholType(List<BalmerPeakRecord> records) {
        if (records == null || records.isEmpty()) {
            return new ArrayList<>();
        }
        return records.stream()
                .map(BalmerPeakRecord::type)
                .distinct()
                .toList();
    }

    public static int getTotalAlcoholAmount(List<BalmerPeakRecord> records) {
        if (records == null || records.isEmpty()) {
            return 0;
        }
        return records.stream()
                .mapToInt(BalmerPeakRecord::amount)
                .sum();
    }
}
