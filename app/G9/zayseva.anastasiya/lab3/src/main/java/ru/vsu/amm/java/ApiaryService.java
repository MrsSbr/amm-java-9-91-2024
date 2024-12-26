package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static ru.vsu.amm.java.Constants.NUMBER_OF_HIVES;

public class ApiaryService {

    public static List<Double> calculateYearlyTotals(List<List<Double>> honeyData) {
        return honeyData.stream()
                .map(year -> year.stream().mapToDouble(Double::doubleValue).sum())
                .toList();
    }


    public static List<Double> calculateTotalHoneyPerHive(List<List<Double>> honeyData) {
        return IntStream.range(0, NUMBER_OF_HIVES)
                .mapToObj(hive -> honeyData.stream()
                        .mapToDouble(year -> year.get(hive))
                        .sum())
                .toList();
    }
}

