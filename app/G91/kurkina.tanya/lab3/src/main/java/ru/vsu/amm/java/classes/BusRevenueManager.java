package ru.vsu.amm.java.classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BusRevenueManager {
    private final List<BusRoute> routes;

    public BusRevenueManager() {
        routes = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            routes.add(new BusRoute(i));
        }
    }

    //возвращает список, содержащий списки доходов за каждый день недели для каждого маршрута
    public List<List<Integer>> collectWeeklyData() {
        Random random = new Random();

        return routes.stream()
                .map(route -> IntStream.range(0, 7)
                        .mapToObj(day -> {
                            int dailyRevenue = random.nextInt(5001);
                            route.addDailyRevenue(dailyRevenue);
                            return dailyRevenue;
                        })
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    //возвращает список строк, где каждая строка представляет текстовый отчёт для одного маршрута
    public List<String> printWeeklyReport() {
        return routes.stream()
                .map(route -> {
                    String report = "ROUTE " + route.getRouteNumber() + ":\n" +
                            " Daily revenue: " + route.getDailyRevenues() + "\n" +
                            " Total for the week: " + route.getTotalWeeklyRevenue() + " rub";
                    return report;
                })
                .collect(Collectors.toList());
    }

    public List<BusRoute> getRoutes() {
        return routes;
    }
}