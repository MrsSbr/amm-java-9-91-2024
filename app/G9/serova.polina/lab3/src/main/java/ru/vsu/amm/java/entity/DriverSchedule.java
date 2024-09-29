package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.Weekday;
import java.util.List;


public final class DriverSchedule {

    private final Driver driver;
    private final List<Route> route;

    public DriverSchedule(Driver driver, List<Route> route) {
        this.driver = driver;
        this.route = route;
    }

    public Driver getDriver() {
        return driver;
    }

    public Route getRouteAt(Weekday weekday) {
        return route.get(weekday.ordinal());
    }
}
