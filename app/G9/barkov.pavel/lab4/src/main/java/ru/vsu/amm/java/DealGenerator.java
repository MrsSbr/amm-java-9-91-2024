package ru.vsu.amm.java;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DealGenerator {
    private static final int COUNT_DIFFERENT_NAMES = 20;
    private static final Double MAX_PRICE = 100000D;
    private static final int BOUND_YEAR = 24;
    private static final int COUNT_MONTHS = 12;
    private static final int COUNT_DAYS = 28;
    private static final int MIN_YEAR = 2000;

    private DealGenerator() {
    }

    public List<Deal> generate(int n) {
        Random random = new Random();
        List<Deal> result = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            result.add(new Deal("Manager" + random.nextInt(COUNT_DIFFERENT_NAMES)
                    , "Client" + random.nextInt(COUNT_DIFFERENT_NAMES)
                    , random.nextDouble(MAX_PRICE)
                    , LocalDate.of(random.nextInt(BOUND_YEAR) + MIN_YEAR
                    , random.nextInt(COUNT_MONTHS)
                    , random.nextInt(COUNT_DAYS))));
        }
        return result;
    }

}
