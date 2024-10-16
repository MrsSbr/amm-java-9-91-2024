package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Driver;
import ru.vsu.amm.java.entity.DriverSchedule;
import ru.vsu.amm.java.entity.Route;

import java.util.List;
import java.util.Set;

public interface StorageService {

    List<DriverSchedule> driverSchedules();
    List<Route> routes();
    List<Double> profits();
    Set<Driver> drivers();
}
