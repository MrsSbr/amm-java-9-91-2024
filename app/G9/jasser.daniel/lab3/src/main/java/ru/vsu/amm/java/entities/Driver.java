package ru.vsu.amm.java.entities;

public class Driver {
    private String name;
    private double expensesPerDay;
    private double totalExpenses;

    public Driver(String name, double expencesPerDay) {
        this.name = name;
        this.expensesPerDay = expencesPerDay;
        totalExpenses = 0.0;
    }

    @Override
    public String toString() {
        return "Driver{" +
                "name='" + name + '\'' +
                ", expencesPerDay=" + expensesPerDay +
                '}';
    }

    public String getName() {
        return name;
    }

    public double getExpenses() {
        return expensesPerDay;
    }

    public void setExpenses(double expensesPerDay) {
        this.expensesPerDay = expensesPerDay;
    }

    public void addToTotalExpenses(double amount) {
        totalExpenses += amount;
    }

    public double getTotalExpenses() {
        return totalExpenses;
    }
}
