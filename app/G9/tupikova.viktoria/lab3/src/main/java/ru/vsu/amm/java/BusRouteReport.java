package ru.vsu.amm.java;

public class BusRouteReport {
    private final int numberRoute;
    private final int profit;

    public BusRouteReport(int numberRoute, int profit) {
        this.numberRoute = numberRoute;
        this.profit = profit;
    }

    public int getNumberRoute() {
        return numberRoute;
    }

    public int getProfit() {
        return profit;
    }

    @Override
    public String toString() {
        return "Route №" + numberRoute + ", profit: " + profit;
    }
}
