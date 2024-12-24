package ru.vsu.amm.java;

import ru.vsu.amm.java.classes.BusRevenueManager;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        BusRevenueManager manager = new BusRevenueManager();

        List<List<Integer>> weeklyData = manager.collectWeeklyData();

        List<String> report = manager.printWeeklyReport();
        report.forEach(System.out::println);
    }
}