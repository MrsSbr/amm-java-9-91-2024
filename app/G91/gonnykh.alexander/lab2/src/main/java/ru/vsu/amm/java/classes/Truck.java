package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.abstractClasses.Car;

import static java.util.Objects.hash;

public class Truck extends Car {
    private final boolean fourWheelDrive;

    public Truck(String make, String model, int year, String engine, int price, boolean fourWheelDrive, String plate) {
        super(make, model, year, engine, price, plate);
        this.fourWheelDrive = fourWheelDrive;
    }

    @Override
    public float calculatePrice() {
        return (fourWheelDrive) ? 2 * startPrice : startPrice;
    }

    @Override
    public String toString() {
        return "Марка: " + make
                + "\nМодель: " + model
                + "\nГод выпуска: " + year
                + "\nМотор: " + engine
                + "\nАренда в день: " + calculatePrice()
                + "\nНомер: " + plate
                + "\nПолный привод: " + fourWheelDrive;
    }

    @Override
    public int hashCode() {
        return hash(make, model, year, engine, startPrice, fourWheelDrive, plate);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Truck && this.hashCode() == obj.hashCode();
    }

    public boolean getFourWheelDrive() {
        return fourWheelDrive;
    }

    @Override
    public void startCar() {
        System.out.println("Вы управляете грузовой машиной");
    }
}
