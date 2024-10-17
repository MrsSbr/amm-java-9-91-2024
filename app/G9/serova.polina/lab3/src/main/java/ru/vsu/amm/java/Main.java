package ru.vsu.amm.java;

import ru.vsu.amm.java.entity.Driver;
import ru.vsu.amm.java.entity.DriverSchedule;
import ru.vsu.amm.java.entity.Route;
import ru.vsu.amm.java.service.*;
import ru.vsu.amm.java.service.StorageServiceImpl;
import ru.vsu.amm.java.util.DriverScheduleGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Main {
    public static void main(String[] args) {
        PassengersCountService passengersCountService = new PassengersCountServiceImpl();
        MostProfitableRouteService mostProfitableRouteService = new MostProfitableRouteServiceImpl();
        List<Route> routes = new ArrayList<>();
        List<Double> profits = new ArrayList<>();
        Set<Driver> drivers = new HashSet<>();
        List<DriverSchedule> driverSchedules = DriverScheduleGenerator.generateScheduleList(13);
        Storage storage = new Storage(driverSchedules, routes, profits, drivers);
        StatsService statsService = new StatsServiceImpl(
                storage,
                passengersCountService,
                mostProfitableRouteService
        );
        statsService.collectStats();
        statsService.printStats();
    }
}