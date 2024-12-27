package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entities.DateRange;
import ru.vsu.amm.java.entities.Driver;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;

public class CalculateExpensesService {
    private OutputInterface out = new ConsoleOutput();

    public void calculateExpenses(DateRange dateRange, int numberWorkingDays, List<Driver> drivers) {
        double total = calculate(dateRange, numberWorkingDays, drivers);
        out.outputTotal(drivers, total);
    }

    public double calculate(DateRange dateRange, int numberWorkingDays, List<Driver> drivers) {
        Random rand = new Random();
        double total = 0;

        if (numberWorkingDays <= 0) {
            numberWorkingDays = dateRange.getEndDate().getDayOfMonth() - dateRange.getStartDate().getDayOfMonth();
        }

        for (LocalDate currentDay = dateRange.getStartDate();
             currentDay.isBefore(dateRange.getEndDate()) && numberWorkingDays > 0;
             currentDay = currentDay.plusDays(1), numberWorkingDays--) {
            for (Driver driver : drivers) {
                driver.setExpenses(rand.nextDouble() * 100);
                out.outputCurrentForDriver(driver);
                driver.addToTotalExpenses(driver.getExpenses());
            }
            double totalPerDay = drivers.stream().mapToDouble(Driver::getExpenses).sum();
            out.outputExpensesInCertainDay(currentDay, totalPerDay);
            total += totalPerDay;
        }

        return total;
    }
}
