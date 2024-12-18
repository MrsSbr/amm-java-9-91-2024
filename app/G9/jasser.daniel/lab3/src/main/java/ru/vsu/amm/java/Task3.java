package ru.vsu.amm.java;

import ru.vsu.amm.java.entities.DateRange;
import ru.vsu.amm.java.entities.Driver;
import ru.vsu.amm.java.services.CalculateExpensesService;
import ru.vsu.amm.java.utils.Generator;

import java.util.*;

public class Task3 {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<String> names = new ArrayList<String>();
        List<String> surnames = new ArrayList<String>();
        fillNames(names);
        fillSurnames(surnames);

        List<Driver> drivers = new ArrayList<Driver>();
        Generator.generateDrivers(drivers, names, surnames);

        DateRange dateRange = new DateRange();
        Generator.generateDates(dateRange);
        System.out.println("Input numbers of working days for analyze:");
        int numberOfWorkingDays = in.nextInt();

        CalculateExpensesService calculateExpenses = new CalculateExpensesService();
        calculateExpenses.calculateExpenses(dateRange, numberOfWorkingDays, drivers);
    }

    public static void fillNames(List<String> names) {
        names.add("John");
        names.add("Jane");
        names.add("Alex");
        names.add("Luis");
        names.add("Mike");
        names.add("Bob");
        names.add("Jack");
        names.add("Jack");
        names.add("Alexey");
        names.add("Dmitrix");
        names.add("Maksim");
        names.add("James");
        names.add("Mark");
    }

    public static void fillSurnames(List<String> surnames) {
        surnames.add("Ivanov");
        surnames.add("Markov");
        surnames.add("Alexeev");
        surnames.add("Suarez");
        surnames.add("Jasser");
        surnames.add("Martinov");
        surnames.add("Plotnikov");
        surnames.add("Falkov");
        surnames.add("Glagolev");
        surnames.add("Ivannikov");
        surnames.add("Suhochev");
        surnames.add("Kirillov");
        surnames.add("Johnson");
    }
}