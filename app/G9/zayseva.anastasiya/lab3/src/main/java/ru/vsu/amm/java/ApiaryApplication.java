package ru.vsu.amm.java;

import java.util.List;
import java.util.Scanner;

public class ApiaryApplication {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество лет для анализа: ");
        int years = scanner.nextInt();

        List<List<Double>> honeyData = ApiaryDataFactory.generateHoneyData(years);

        ApiaryService.printYearlyStatistics(honeyData);
        System.out.println("========================================\n");

        List<Double> totalHoneyPerHive = ApiaryService.calculateTotalHoneyPerHive(honeyData);
        ApiaryService.printTotalHoneyPerHive(totalHoneyPerHive, years);
    }
}