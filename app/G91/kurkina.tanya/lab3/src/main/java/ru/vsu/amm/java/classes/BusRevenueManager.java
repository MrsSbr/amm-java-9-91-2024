package ru.vsu.amm.java.classes;

import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class BusRevenueManager {
    private final List<BusRoute> routes;

    public BusRevenueManager(){
        routes = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            routes.add(new BusRoute(i));
        }
    }

    public void collectWeeklyData() {
        Random random = new Random();
        for (BusRoute route : routes) {
            for (int day = 0; day < 7; day++) {
                route.addDailyRevenue(random.nextInt(5001));
            }
        }
    }

    public void printWeeklyReport() {
        System.out.println("Total weekly revenue report: ");
        for (BusRoute route : routes) {
            System.out.println("ROUTE " + route.getRouteNumber() + ": ");
            System.out.println(" Daily revenue: " + route.getDailyRevenues());
            System.out.println( "Total for the week: "+ route.getWeeklyRevenue() + " rub");
        }
    }

    public List<BusRoute> getRoutes() {
        return routes;
    }
}
