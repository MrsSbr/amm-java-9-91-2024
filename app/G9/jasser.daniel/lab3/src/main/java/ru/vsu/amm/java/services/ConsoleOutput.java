package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entities.Driver;

import java.time.LocalDate;
import java.util.List;

public class ConsoleOutput implements OutputInterface {
    public void outputExpensesInCertainDay(LocalDate currentDay, double totalPerDay) {
        System.out.println("\nExpenses in " + currentDay);
        System.out.println("Day " + currentDay + " expenses " + totalPerDay + '\n');
    }

    public void outputCurrentForDriver(Driver driver) {
        System.out.printf("Driver %s: %.2f liters per day%n", driver.getName(), driver.getExpenses());
    }

    public void outputTotal(List<Driver> drivers, double total) {
        for (Driver driver : drivers) {
            System.out.println("Total expenses for driver : ");
            System.out.println(driver.getName() + ": " + driver.getTotalExpenses());
        }
        System.out.println("Total expenses: " + ' ' + total);
    }
}
