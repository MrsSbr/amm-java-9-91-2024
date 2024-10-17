package ru.vsu.amm.java.service;

import ru.vsu.amm.java.enums.Weekday;
import ru.vsu.amm.java.entity.Driver;
import ru.vsu.amm.java.entity.Route;

public interface PassengersCountService {
    int getPassengersCount(Driver driver, Route route, Weekday weekday);
}
