package ru.vsu.amm.java;

import java.time.LocalDate;

public class Deal {
    private final String manager;
    private final String client;
    private final Double price;
    private final LocalDate date;

    public Deal(String manager, String client, Double price, LocalDate date) {
        this.manager = manager;
        this.client = client;
        this.price = price;
        this.date = date;
    }

    public String getManager() {
        return manager;
    }

    public String getClient() {
        return client;
    }

    public Double getPrice() {
        return price;
    }

    public LocalDate getDate() {
        return date;
    }

    @Override
    public String toString() {
        return manager + ',' + client + ',' + price + ',' + date;
    }
}
