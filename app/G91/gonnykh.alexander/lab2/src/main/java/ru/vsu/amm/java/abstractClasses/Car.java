package ru.vsu.amm.java.abstractClasses;

import ru.vsu.amm.java.interfaces.Vehicle;

public abstract class Car implements Vehicle {
    protected String make;
    protected String model;
    protected int year;
    protected String engine;
    protected float startPrice;
    protected String plate;

    public Car(String make, String model, int year, String engine, float price, String plate) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.engine = engine;
        this.startPrice = price;
        this.plate = plate;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }


    public int getYear() {
        return year;
    }

    public String getEngine() {
        return engine;
    }

    public float getPrice() {
        return startPrice;
    }

    public String getPlate() {
        return plate;
    }

    public abstract float calculatePrice();
}
