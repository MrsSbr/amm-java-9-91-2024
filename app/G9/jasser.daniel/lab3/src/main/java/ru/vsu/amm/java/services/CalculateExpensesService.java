package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entities.DateRange;
import ru.vsu.amm.java.entities.Driver;

import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class CalculateExpensesService {
    private OutputInterface out = new ConsoleOutput();
    private Random rand = new Random();

    public void calculateExpenses(DateRange dateRange, int numberWorkingDays, List<Driver> drivers) {
        double sum = calculate(dateRange, numberWorkingDays, drivers);
        out.outputTotal(drivers, sum);
    }

    public double calculate(DateRange dateRange, int numberWorkingDays, List<Driver> drivers) {
        for (Calendar currentDay = dateRange.getStartDate();
             currentDay.before(dateRange.getEndDate()) && numberWorkingDays > 0;
             currentDay.add(Calendar.DAY_OF_MONTH, 1), numberWorkingDays--) {
            for (Driver driver : drivers) {
                driver.setExpenses(rand.nextDouble()*1000);
                out.outputCurrentForDriver(driver);
                driver.addToTotalExpenses(driver.getExpenses());
            }
            out.outputExpensesInCertainDay(currentDay, drivers);
        }

        return drivers.stream()
                .collect(Collectors.summarizingDouble(Driver::getExpenses))
                .getSum();
    }
}
