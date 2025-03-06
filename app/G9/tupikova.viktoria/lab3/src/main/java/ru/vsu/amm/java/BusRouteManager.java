package ru.vsu.amm.java;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class BusRouteManager {
    public static final int MIN_NUMBER_OF_ROUTES = 1;
    public static final int MAX_NUMBER_OF_ROUTES = 10;
    private List<BusRouteReport> weeklyReports;
    private final Random random;

    public BusRouteManager() {
        weeklyReports = new ArrayList<BusRouteReport>();
        random = new Random();
    }

    public void generateReports() {
        int reportsPerRoute = 7;
        weeklyReports = IntStream.range(0, reportsPerRoute * MAX_NUMBER_OF_ROUTES)
                .mapToObj(i -> {
                    int routeNumber = (i % MAX_NUMBER_OF_ROUTES) + 1;
                    int profit = random.nextInt(20000);
                    return new BusRouteReport(routeNumber, profit);
                })
                .collect(Collectors.toList());
    }

    public int getWeeklyProfit() {
        return weeklyReports.stream().mapToInt(BusRouteReport::getProfit).sum();
    }

    public int getWeeklyProfitByRoute(int routeNumber) {
        if(routeNumber < MIN_NUMBER_OF_ROUTES || routeNumber > MAX_NUMBER_OF_ROUTES) {
            throw new IllegalArgumentException("Invalid route number: " + routeNumber);
        }
        return weeklyReports.stream()
                .filter(report -> report.getNumberRoute() == routeNumber)
                .mapToInt(BusRouteReport::getProfit)
                .sum();
    }

    public List<BusRouteReport> getReports() {
        return weeklyReports;
    }
}
