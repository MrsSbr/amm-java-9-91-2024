package ru.vsu.amm.java.abstractClasses;

import ru.vsu.amm.java.interfaces.Startable;

import static java.util.Objects.hash;

public abstract class Car implements Startable {
    protected String make;
    protected String model;
    protected int year;
    protected String engine;
    protected double startPrice;
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

    public double getPrice() {
        return startPrice;
    }

    public String getPlate() {
        return plate;
    }

    public abstract double calculatePrice();

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Car objCast = (Car) obj;
        return make.equals(objCast.make) && model.equals(objCast.model) && year == objCast.year
                && engine.equals(objCast.engine) && startPrice == objCast.startPrice
                && plate.equals(objCast.plate);
    }

    @Override
    public int hashCode() {
        return hash(make, model, year, engine, startPrice, plate);
    }
}
