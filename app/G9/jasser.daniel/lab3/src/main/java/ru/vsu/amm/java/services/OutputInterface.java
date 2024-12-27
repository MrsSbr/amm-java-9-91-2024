package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entities.Driver;

import java.time.LocalDate;
import java.util.List;

public interface OutputInterface {
    public void outputExpensesInCertainDay(LocalDate currentDay, double totalPerDay);

    public void outputCurrentForDriver(Driver driver);

    public void outputTotal(List<Driver> drivers, double total);
}
