package ru.vsu.amm.java;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class ApiaryDataFactory {
    private static final int MIN = 10;

    private static final int MAX = 50;

    public static List<List<Double>> generateHoneyData(int years) {
        Random random = new Random();
        return IntStream.range(0, years)
                .mapToObj(year -> IntStream.range(0, Constants.NUMBER_OF_HIVES)
                        .mapToObj(hive -> MIN + (MAX - MIN) * random.nextDouble())
                        .toList())
                .toList();
    }
}
