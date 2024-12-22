package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;

public class BusRoute {
    private final int routeNumber;
    private final List<Integer> dailyRevenues;

    public BusRoute(int routeNumber) {
        this.routeNumber = routeNumber;
        this.dailyRevenues = new ArrayList<>();
    }

    public void addDailyRevenue(int revenue) {
        dailyRevenues.add(revenue);
    }

    public int getWeeklyRevenue(){
        return dailyRevenues.stream().reduce(0, Integer::sum); //итоговая выручка за неделю
        //reduce накапливает значения
    }

    public int getRouteNumber() {
        return routeNumber;
    }

    public List<Integer> getDailyRevenues() {
        return dailyRevenues;
    }
}