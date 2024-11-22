package ru.vsu.amm.java.services;

import java.time.LocalDate;

public class Deal {
    private final String manager;
    private final String client;
    private final double amount;
    private final LocalDate date;

    public Deal(String manager, String client, double amount, LocalDate date) {
        this.manager = manager;
        this.client = client;
        this.amount = amount;
        this.date = date;
    }

    public String getManager() {
        return manager;
    }

    public String getClient() {
        return client;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDate() {
        return date;
    }
}
