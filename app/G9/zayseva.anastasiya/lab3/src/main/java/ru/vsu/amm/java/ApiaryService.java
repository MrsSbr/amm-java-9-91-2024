package ru.vsu.amm.java;

import java.util.List;
import java.util.stream.IntStream;

public class ApiaryService {

    public static void printYearlyStatistics(List<List<Double>> honeyData) {
        for (int year = 0; year < honeyData.size(); year++) {
            double totalHoney = honeyData.get(year).stream().mapToDouble(Double::doubleValue).sum();
            System.out.printf("Год %d: общий объем меда = %f кг\n", year + 1, totalHoney);
        }
    }

    public static List<Double> calculateTotalHoneyPerHive(List<List<Double>> honeyData) {
        return IntStream.range(0, Constants.NUMBER_OF_HIVES)
                .mapToObj(hive -> honeyData.stream()
                        .mapToDouble(year -> year.get(hive))
                        .sum())
                .toList();
    }

    public static void printTotalHoneyPerHive(List<Double> totalHoneyPerHive, int years) {
        for (int hive = 0; hive < Constants.NUMBER_OF_HIVES; hive++) {
            System.out.printf("Улей %d: общий объем меда за %d лет = %.2f кг\n", hive + 1, years, totalHoneyPerHive.get(hive));
        }
    }
}
