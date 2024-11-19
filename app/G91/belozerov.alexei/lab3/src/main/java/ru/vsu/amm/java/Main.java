package ru.vsu.amm.java;

import ru.vsu.amm.java.classes.BalmerPeakRecord;
import ru.vsu.amm.java.service.BalmerPeakService;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Generating Balmer Peak Records...");
        List<BalmerPeakRecord> records = BalmerPeakService.generateRecords();
        System.out.println();

        System.out.println("First 10 records");
        records.stream().limit(10).forEach(System.out::println);
        System.out.println();

        System.out.print("Average amount of alcohol for Balmer peak: ");
        System.out.println(String.format("%.2f", BalmerPeakService.getAverageAmountForPeak(records)));
        System.out.println();

        System.out.println("Unique types of alcohol: ");
        BalmerPeakService.getUniqueAlcoholType(records).forEach(System.out::println);
        System.out.println();

        System.out.print("Total amount of alcohol: ");
        System.out.println(BalmerPeakService.getTotalAlcoholAmount(records));
    }
}