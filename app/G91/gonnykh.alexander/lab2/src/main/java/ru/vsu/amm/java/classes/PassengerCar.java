package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.abstractClasses.Car;
import ru.vsu.amm.java.enums.ComfortLevel;

import static java.util.Objects.hash;

public class PassengerCar extends Car {
    private ComfortLevel comfortLevel;

    public PassengerCar(String make, String model, int year, String engine, float price, ComfortLevel comfortLevel, String plate) {
        super(make, model, year, engine, price, plate);
        this.comfortLevel = comfortLevel;
    }

    public ComfortLevel getComfortLevel() {
        return comfortLevel;
    }

    @Override
    public double calculatePrice() {
        double cost = startPrice;
        switch (comfortLevel) {
            case COMFORT -> cost *= 2;
            case COMFORT_PLUS -> cost *= 3;
            case BUSINESS -> cost *= 4;
        }
        return cost;
    }

    @Override
    public void startCar() {
        System.out.println("Вы управляете пассажирской машиной");
    }

    @Override
    public String toString() {
        return "Марка: " + make
                + "\nМодель: " + model
                + "\nГод выпуска: " + year
                + "\nМотор: " + engine
                + "\nАренда в день: " + calculatePrice()
                + "\nНомер: " + plate
                + "\nУровень комфорта: " + comfortLevel;
    }

    @Override
    public int hashCode() {
        return hash(super.hashCode(), comfortLevel);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj) || getClass() != obj.getClass()) {
            return false;
        }
        PassengerCar objCast = (PassengerCar) obj;
        return comfortLevel == objCast.comfortLevel;
    }
}
