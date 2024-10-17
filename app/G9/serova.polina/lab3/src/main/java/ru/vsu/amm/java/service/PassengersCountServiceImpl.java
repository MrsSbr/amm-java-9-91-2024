package ru.vsu.amm.java.service;

import ru.vsu.amm.java.enums.Weekday;
import ru.vsu.amm.java.entity.Driver;
import ru.vsu.amm.java.entity.Route;

import java.util.Random;


public final class PassengersCountServiceImpl implements PassengersCountService {

    private final Random random;

    public PassengersCountServiceImpl() {
        this.random = new Random();
    }

    @Override
    public int getPassengersCount(Driver driver, Route route, Weekday weekday) {
        return random.nextInt(200);
    }
}
