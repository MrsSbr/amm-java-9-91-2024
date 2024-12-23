package ru.vsu.amm.java;

import ru.vsu.amm.java.classes.BusRevenueManager;

public class Main {
    public static void main(String[] args) {
        BusRevenueManager manager = new BusRevenueManager();
        manager.collectWeeklyData(); // Генерация данных по неделе
        manager.printWeeklyReport(); // Печать отчета
    }
}