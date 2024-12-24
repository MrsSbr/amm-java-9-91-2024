package ru.vsu.amm.java;

import java.time.LocalDate;
import java.util.List;

public class FeedingRecord {
    private LocalDate date;
    private String animalName;
    private List<String> products;
    private double foodWeight;

    public FeedingRecord(LocalDate date, String animalName, List<String> products, double foodWeight) {
        this.date = date;
        this.animalName = animalName;
        this.products = products;
        this.foodWeight = foodWeight;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getAnimalName() {
        return animalName;
    }

    public List<String> getProducts() {
        return products;
    }

    public double getFoodWeight() {
        return foodWeight;
    }
}