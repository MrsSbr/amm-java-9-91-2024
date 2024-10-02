package ru.vsu.amm.java.generate;

import ru.vsu.amm.java.winners.StorageWinners;
import ru.vsu.amm.java.winners.Winner;

import java.util.Random;
import java.util.stream.IntStream;

public class RandomWinnerGenerator {
    private static final Random random = new Random();
    private static final String[] names = {
            "Alice", "Bob", "Charlie", "David", "Eve", "Frank", "Grace", "Helen", "Ivy", "Jack"
    };
    private static final String[] departments = {
            "HR", "IT", "Finance", "Marketing", "Sales", "R&D", "Support", "Management"
    };
    private static final int START_YEAR = 1973; // 50 лет назад от 2023
    private static final int END_YEAR = 2023;

    public static Winner generateRandomWinner() {
        int year = random.nextInt(END_YEAR - START_YEAR + 1) + START_YEAR;
        String name = names[random.nextInt(names.length)];
        String departmentName = departments[random.nextInt(departments.length)];

        return new Winner(year, name, departmentName);
    }

    public static void populateStorageWinners(StorageWinners<Winner> storage, int numberOfWinners) {
        IntStream.range(0, numberOfWinners)
                .mapToObj(i -> generateRandomWinner())
                .forEach(storage::add);
    }
}
