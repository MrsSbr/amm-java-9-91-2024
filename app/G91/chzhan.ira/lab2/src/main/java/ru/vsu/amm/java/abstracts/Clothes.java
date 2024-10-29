package ru.vsu.amm.java.abstracts;

import ru.vsu.amm.java.interfaces.Wearable;

public abstract class Clothes implements Wearable {


    protected String name;
    protected String description;
    protected double price;

    public Clothes(String name, String description, double price) {
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
