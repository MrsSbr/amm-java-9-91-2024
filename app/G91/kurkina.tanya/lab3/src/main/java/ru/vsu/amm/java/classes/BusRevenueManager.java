package ru.vsu.amm.java.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class BusRevenueManager {
    private final List<BusRoute> routes;

    public BusRevenueManager() {
        routes = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            routes.add(new BusRoute(i));
        }
    }

    public void collectWeeklyData() {
        Random random = new Random();
        routes.forEach(route ->
                IntStream.range(0, 7)
                        .forEach(day -> route.addDailyRevenue(random.nextInt(5001)))
        );
    }

    // Итоговый отчет
    public void printWeeklyReport() {
        System.out.println("Total weekly revenue report:");
        routes.stream()
                .forEach(route -> {
                    System.out.println("ROUTE " + route.getRouteNumber() + ":");
                    System.out.println(" Daily revenue: " + route.getDailyRevenues());
                    System.out.println(" Total for the week: " + route.getTotalWeeklyRevenue() + " rub");
                });
    }

    public List<BusRoute> getRoutes() {
        return routes;
    }
}