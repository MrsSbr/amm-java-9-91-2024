package ru.vsu.amm.java.mocks;

import ru.vsu.amm.java.entity.Driver;
import ru.vsu.amm.java.entity.Route;
import ru.vsu.amm.java.enums.Weekday;
import ru.vsu.amm.java.service.PassengersCountService;

public class PassengersCountServiceMock implements PassengersCountService {

    public Integer stubbedGetPassengersCountResult;
    public int invokedGetPassengersCount;

    public PassengersCountServiceMock() {
        this.stubbedGetPassengersCountResult = null;
        this.invokedGetPassengersCount = 0;
    }

    public void reset() {
        this.stubbedGetPassengersCountResult = null;
        this.invokedGetPassengersCount = 0;
    }

    @Override
    public int getPassengersCount(Driver driver, Route route, Weekday weekday) {
        this.invokedGetPassengersCount += 1;
        return this.stubbedGetPassengersCountResult;
    }
}
