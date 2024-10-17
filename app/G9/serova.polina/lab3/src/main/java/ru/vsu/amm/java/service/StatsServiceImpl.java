package ru.vsu.amm.java.service;

import ru.vsu.amm.java.enums.Weekday;
import ru.vsu.amm.java.entity.Driver;
import ru.vsu.amm.java.entity.DriverSchedule;
import ru.vsu.amm.java.entity.Route;

import static ru.vsu.amm.java.Constants.ROUTES_COUNT;


public final class StatsServiceImpl implements StatsService {

    private final PassengersCountService passengersCountService;
    private final MostProfitableRouteService mostProfitableRouteService;
    private final Storage storageService;

    public StatsServiceImpl(
            Storage storageService,
            PassengersCountService passengersCountService,
            MostProfitableRouteService mostProfitableRouteService
    ) {
        this.passengersCountService = passengersCountService;
        this.mostProfitableRouteService = mostProfitableRouteService;
        this.storageService = storageService;
    }

    @Override
    public void printStats() {
        if(storageService.routes().size() != ROUTES_COUNT
                || storageService.profits().size() != ROUTES_COUNT
                || storageService.drivers().isEmpty()) {
            System.out.println("Статистика не собрана.");
        }

        StringBuilder routesStats = new StringBuilder();
        routesStats.append("Статистика по маршрутам:\n");
        for(int i = 0; i < ROUTES_COUNT; ++i) {
            routesStats.append(storageService.routes().get(i)).append("\n");
            routesStats.append("Прибыль: ").append(storageService.profits().get(i)).append("\n\n");
        }
        System.out.println(routesStats);

        StringBuilder driversStats = new StringBuilder();
        driversStats.append("Статистика по водителям:\n");
        for(Driver driver: storageService.drivers()) {
            driversStats.append(driver).append("\n\n");
        }
        System.out.println(driversStats);

        Route mostProfitableRoute = mostProfitableRouteService.mostProfitableRoute(
                storageService.routes(),
                storageService.profits()
        );
        System.out.println("Самый прибыльный маршрут: " + mostProfitableRoute.number());
    }

    @Override
    public void collectStats() {
        for(DriverSchedule currentSchedule: storageService.driverSchedules()) {
            for(Weekday weekday: Weekday.values()) {
                Driver currentDriver = currentSchedule.getDriver();
                Route currentRoute = currentSchedule.getRouteAt(weekday);
                storageService.drivers().add(currentDriver);
                int currentRouteIndex = storageService.routes().indexOf(currentRoute);
                if (currentRouteIndex == -1) {
                    storageService.routes().add(currentRoute);
                    storageService.profits().add(0.);
                    currentRouteIndex = storageService.routes().size() - 1;
                }
                storageService.profits().set(
                        currentRouteIndex,
                        storageService.profits().get(currentRouteIndex)
                                + currentRoute.price()
                                * (double) passengersCountService.getPassengersCount(currentDriver, currentRoute, weekday)
                );
            }
        }
    }
}
