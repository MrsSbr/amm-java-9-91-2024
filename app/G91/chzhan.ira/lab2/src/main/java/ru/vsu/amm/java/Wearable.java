package ru.vsu.amm.java;

public abstract class Wearable implements Clothing {

    protected String name;
    protected String description;
    protected double price;

    public Wearable(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public abstract String toString();
}
