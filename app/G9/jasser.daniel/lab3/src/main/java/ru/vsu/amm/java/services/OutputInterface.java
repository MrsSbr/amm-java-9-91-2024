package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entities.Driver;

import java.util.Calendar;
import java.util.List;

public interface OutputInterface {
    public void outputExpensesInCertainDay(Calendar currentDay, List<Driver> drivers);

    public void outputCurrentForDriver(Driver driver);

    public void outputTotal(List<Driver> drivers, double total);
}
