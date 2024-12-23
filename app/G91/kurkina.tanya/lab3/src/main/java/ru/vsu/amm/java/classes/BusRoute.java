package ru.vsu.amm.java.classes;

import java.util.ArrayList;
import java.util.List;

public class BusRoute {
    private final int routeNumber;
    private final List<Integer> dailyRevenues; // Выручка за каждый день недели по маршруту

    public BusRoute(int routeNumber) {
        this.routeNumber = routeNumber;
        this.dailyRevenues = new ArrayList<>();
    }

    public void addDailyRevenue(int revenue) {
        dailyRevenues.add(revenue);
    }

    public int getTotalWeeklyRevenue() {
        return dailyRevenues.stream().reduce(0, Integer::sum);
    }

    public int getRouteNumber() {
        return routeNumber;
    }

    public List<Integer> getDailyRevenues() {
        return dailyRevenues;
    }
}
