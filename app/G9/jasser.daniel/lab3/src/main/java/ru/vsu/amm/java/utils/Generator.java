package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.entities.DateRange;
import ru.vsu.amm.java.entities.Driver;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Random;

import static ru.vsu.amm.java.utils.Constants.COUNT_DRIVERS;

public class Generator {
    public static void generateDrivers(List<Driver> drivers, List<String> names, List<String> surnames) {
        Random rand = new Random();

        for (int i = 0; i < COUNT_DRIVERS; i++) {
            Driver driver = new Driver(names.get(Math.abs(rand.nextInt() % names.size()))
                    + " " + surnames.get(Math.abs(rand.nextInt() % surnames.size())),
                    0);
            drivers.add(driver);
        }
    }

    public static void generateDates(DateRange dateRange) {
        Random rand = new Random();

        LocalDate startDate = LocalDate.of(2023, Month.JANUARY, 1);
        int randomDays = Math.abs(rand.nextInt()) % 365;
        startDate = startDate.plusDays(randomDays);

        dateRange.setStartDate(startDate);

        LocalDate endDate = startDate;
        randomDays = Math.abs(rand.nextInt()) % 365;
        endDate = endDate.plusDays(randomDays);
        dateRange.setEndDate(endDate);
    }
}
