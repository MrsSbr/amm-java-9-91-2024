package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entities.Driver;

import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class ConsoleOutput implements OutputInterface {
    public void outputExpensesInCertainDay(Calendar currentDay, List<Driver> drivers) {
        System.out.println("\nExpenses in" + currentDay.getTime().toString());
        double totalPerDay = drivers.stream()
                .collect(Collectors.summarizingDouble(Driver::getExpenses))
                .getSum();
        System.out.println("Day " + currentDay.getTime().toString() + " expenses " + totalPerDay + '\n');
    }

    public void outputCurrentForDriver(Driver driver) {
        System.out.printf("Driver %s: %.2f liters per day%n", driver.getName(), driver.getExpenses());
    }

    public void outputTotal(List<Driver> drivers, double total) {
        for (Driver driver : drivers) {
            System.out.println("Total expenses for driver : ");
            System.out.println(driver.getName() + ": " + driver.getTotalExpenses());
        }
        System.out.println("Total expenses: " + total);
    }
}
