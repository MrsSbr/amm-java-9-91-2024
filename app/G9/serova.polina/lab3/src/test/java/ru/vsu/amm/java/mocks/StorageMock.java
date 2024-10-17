package ru.vsu.amm.java.mocks;

import ru.vsu.amm.java.entity.Driver;
import ru.vsu.amm.java.entity.DriverSchedule;
import ru.vsu.amm.java.entity.Route;
import ru.vsu.amm.java.service.Storage;

import java.util.List;
import java.util.Set;


public class StorageServiceMock implements Storage {

    public List<DriverSchedule> stubbedDriverSchedulesResult;
    public List<Route> stubbedRoutesResult;
    public List<Double> stubbedProfitsResult;
    public Set<Driver> stubbedDriversResult;

    public int invokedDriverSchedulesCount;
    public int invokedRoutesCount;
    public int invokedProfitsCount;
    public int invokedDriversCount;

    public StorageServiceMock() {
        this.stubbedDriverSchedulesResult = null;
        this.stubbedRoutesResult = null;
        this.stubbedProfitsResult = null;
        this.stubbedDriversResult = null;
        this.invokedDriverSchedulesCount = 0;
        this.invokedRoutesCount = 0;
        this.invokedProfitsCount = 0;
        this.invokedDriversCount = 0;
    }

    public void reset() {
        this.stubbedDriverSchedulesResult = null;
        this.stubbedRoutesResult = null;
        this.stubbedProfitsResult = null;
        this.stubbedDriversResult = null;
        this.invokedDriverSchedulesCount = 0;
        this.invokedRoutesCount = 0;
        this.invokedProfitsCount = 0;
        this.invokedDriversCount = 0;
    }

    @Override
    public List<DriverSchedule> driverSchedules() {
        this.invokedDriverSchedulesCount += 1;
        return this.stubbedDriverSchedulesResult;
    }

    @Override
    public List<Route> routes() {
        this.invokedRoutesCount += 1;
        return this.stubbedRoutesResult;
    }

    @Override
    public List<Double> profits() {
        this.invokedProfitsCount += 1;
        return this.stubbedProfitsResult;
    }

    @Override
    public Set<Driver> drivers() {
        this.invokedRoutesCount += 1;
        return this.stubbedDriversResult;
    }
}
