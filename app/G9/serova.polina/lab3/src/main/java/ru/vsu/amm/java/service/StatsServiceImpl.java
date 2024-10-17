package ru.vsu.amm.java.service;

import ru.vsu.amm.java.enums.Weekday;
import ru.vsu.amm.java.entity.Driver;
import ru.vsu.amm.java.entity.DriverSchedule;
import ru.vsu.amm.java.entity.Route;

import static ru.vsu.amm.java.Constants.ROUTES_COUNT;


public final class StatsServiceImpl implements StatsService {

    private final PassengersCountService passengersCountService;
    private final MostProfitableRouteService mostProfitableRouteService;
    private final Storage storage;

    public StatsServiceImpl(
            Storage storage,
            PassengersCountService passengersCountService,
            MostProfitableRouteService mostProfitableRouteService
    ) {
        this.passengersCountService = passengersCountService;
        this.mostProfitableRouteService = mostProfitableRouteService;
        this.storage = storage;
    }

    @Override
    public void printStats() {
        if (storage.routes().size() != ROUTES_COUNT
                || storage.profits().size() != ROUTES_COUNT
                || storage.drivers().isEmpty()) {
            System.out.println("Статистика не собрана.");
        }

        StringBuilder routesStats = new StringBuilder();
        routesStats.append("Статистика по маршрутам:\n");
        for (int i = 0; i < ROUTES_COUNT; ++i) {
            routesStats.append(storage.routes().get(i)).append("\n");
            routesStats.append("Прибыль: ").append(storage.profits().get(i)).append("\n\n");
        }
        System.out.println(routesStats);

        StringBuilder driversStats = new StringBuilder();
        driversStats.append("Статистика по водителям:\n");
        for (Driver driver : storage.drivers()) {
            driversStats.append(driver).append("\n\n");
        }
        System.out.println(driversStats);

        Route mostProfitableRoute = mostProfitableRouteService.mostProfitableRoute(
                storage.routes(),
                storage.profits()
        );
        System.out.println("Самый прибыльный маршрут: " + mostProfitableRoute.number());
    }

    @Override
    public void collectStats() {
        for (DriverSchedule currentSchedule : storage.driverSchedules()) {
            for (Weekday weekday : Weekday.values()) {
                Driver currentDriver = currentSchedule.getDriver();
                Route currentRoute = currentSchedule.getRouteAt(weekday);
                storage.drivers().add(currentDriver);
                int currentRouteIndex = storage.routes().indexOf(currentRoute);
                if (currentRouteIndex == -1) {
                    storage.routes().add(currentRoute);
                    storage.profits().add(0.);
                    currentRouteIndex = storage.routes().size() - 1;
                }
                storage.profits().set(
                        currentRouteIndex,
                        storage.profits().get(currentRouteIndex)
                                + currentRoute.price()
                                * (double) passengersCountService.getPassengersCount(currentDriver, currentRoute, weekday)
                );
            }
        }
    }
}
