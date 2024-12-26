package ru.vsu.amm.java;

import java.util.List;
import java.util.Scanner;

public class ApiaryApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите год: ");
        int years = scanner.nextInt();

        List<List<Double>> honeyData = ApiaryDataFactory.generateHoneyData(years);

        // Get yearly statistics
        List<Double> yearlyTotals = ApiaryService.calculateYearlyTotals(honeyData);
        List<Double> totalHoneyPerHive = ApiaryService.calculateTotalHoneyPerHive(honeyData);

        // Print yearly statistics
        for (int year = 0; year < yearlyTotals.size(); year++) {
            System.out.println(String.format("Год %d: Всего меда = %.2f кг", year + 1, yearlyTotals.get(year)));
        }
        System.out.println("========================================\n");

        // Print total honey per hive statistics
        for (int hive = 0; hive < totalHoneyPerHive.size(); hive++) {
            System.out.println(String.format("Улей %d: Общее количество меда за %d лет = %.2f кг", hive + 1, years, totalHoneyPerHive.get(hive)));
        }
    }
}
