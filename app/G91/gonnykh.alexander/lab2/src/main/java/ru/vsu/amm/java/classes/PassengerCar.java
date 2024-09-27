package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.abstractClasses.Car;

import static java.util.Objects.hash;

public class PassengerCar extends Car {
    private int comfortLevel;

    public PassengerCar(String make, String model, int year, String engine, float price, int comfortLevel, String plate) {
        super(make, model, year, engine, price, plate);
        this.comfortLevel = comfortLevel;
    }

    @Override
    public float calculatePrice() {
        return comfortLevel * startPrice;
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

    public int getComfortLevel() {
        return comfortLevel;
    }

    @Override
    public void startCar() {
        System.out.println("Вы управляете пассажирской машиной");
    }
}
