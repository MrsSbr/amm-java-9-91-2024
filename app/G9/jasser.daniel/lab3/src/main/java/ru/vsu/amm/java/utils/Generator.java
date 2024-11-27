package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.entities.DateRange;
import ru.vsu.amm.java.entities.Driver;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

public class Generator {
    public static List<Driver> generateDrivers(List<Driver> drivers, List<String> names, List<String> surnames) {
        final int COUNT_DRIVERS = 20;
        Random rand = new Random();

        for (int i = 0; i < COUNT_DRIVERS; i++) {
            Driver driver = new Driver(names.get(Math.abs(rand.nextInt() % names.size()))
                    + " " + surnames.get(Math.abs(rand.nextInt() % surnames.size())),
                    0);
            drivers.add(driver);
        }

        return drivers;
    }

    public static void generateDates(DateRange dateRange) {
        Random rand = new Random();

        Calendar forRandomStart = new GregorianCalendar(2023, Calendar.JANUARY, 1);
        forRandomStart.add(Calendar.DAY_OF_MONTH, rand.nextInt() * 365);
        dateRange.setStartDate(forRandomStart);

        Calendar forRandomEnd = dateRange.getStartDate();
        forRandomEnd.add(Calendar.DAY_OF_MONTH, rand.nextInt() * 365);
        dateRange.setStartDate(forRandomEnd);
    }
}
