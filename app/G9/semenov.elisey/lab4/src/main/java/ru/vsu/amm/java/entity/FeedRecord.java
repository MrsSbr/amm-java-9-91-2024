package ru.vsu.amm.java.entity;

import java.time.LocalDate;
import java.util.List;

public class FeedRecord {

    private LocalDate date;

    private String name;

    private List<String> products;

    private double weight;

    public FeedRecord(LocalDate date,
                      String name,
                      List<String> products,
                      double weight) {
        this.date = date;
        this.name = name;
        this.products = products;
        this.weight = weight;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getProducts() {
        return products;
    }

    public void setProducts(List<String> products) {
        this.products = products;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
