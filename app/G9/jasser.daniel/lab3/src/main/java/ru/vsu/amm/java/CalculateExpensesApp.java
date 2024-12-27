package ru.vsu.amm.java;

import ru.vsu.amm.java.entities.DateRange;
import ru.vsu.amm.java.entities.Driver;
import ru.vsu.amm.java.services.CalculateExpensesService;
import ru.vsu.amm.java.utils.Generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CalculateExpensesApp {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<String> names = new ArrayList<String>(Arrays.asList("John", "Jane", "Alex", "Luis", "Mike", "Bob", "Jack"));
        List<String> surnames = new ArrayList<String>(Arrays.asList("Ivanov", "Markov", "Alexeev", "Plotnicov", "Suarez"));

        List<Driver> drivers = new ArrayList<Driver>();
        Generator.generateDrivers(drivers, names, surnames);

        DateRange dateRange = new DateRange();
        Generator.generateDates(dateRange);
        System.out.println("Time period for analyze: " + dateRange.getStartDate() + "  " + dateRange.getEndDate());
        System.out.println("Input numbers of working days for analyze:");
        int numberOfWorkingDays = in.nextInt();

        CalculateExpensesService calc = new CalculateExpensesService();
        calc.calculateExpenses(dateRange, numberOfWorkingDays, drivers);
    }
}