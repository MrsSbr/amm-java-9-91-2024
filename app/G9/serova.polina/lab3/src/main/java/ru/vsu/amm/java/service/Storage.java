package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.Driver;
import ru.vsu.amm.java.entity.DriverSchedule;
import ru.vsu.amm.java.entity.Route;
import java.util.List;
import java.util.Set;


public class Storage {
        List<DriverSchedule> driverSchedules;
        List<Route> routes;
        List<Double> profits;
        Set<Driver> drivers;

        public Storage() {
        }

        public Storage(
                List<DriverSchedule> driverSchedules,
                List<Route> routes,
                List<Double> profits,
                Set<Driver> drivers) {
            this.driverSchedules = driverSchedules;
            this.routes = routes;
            this.profits = profits;
            this.drivers = drivers;
        }

        public List<DriverSchedule> driverSchedules() {
            return driverSchedules;
        }

        public List<Route> routes() {
            return routes;
        }

        public List<Double> profits() {
            return profits;
        }

        public Set<Driver> drivers() {
            return drivers;
        }
}