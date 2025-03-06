package ru.vsu.amm.java;

public class CityBus {
    public static void main(String[] args) {
        BusRouteManager manager = new BusRouteManager();
        manager.generateReports();

        System.out.println("Weekly profit: " + manager.getWeeklyProfit());

        System.out.println("\nWeekly profit by route:");
        for(int i = BusRouteManager.MIN_NUMBER_OF_ROUTES; i <= BusRouteManager.MAX_NUMBER_OF_ROUTES; i++) {
            System.out.println("Route № " + i + ": " + manager.getWeeklyProfitByRoute(i));
        }

        System.out.println("\nAll reports:");
        manager.getReports().forEach(System.out::println);
    }
}