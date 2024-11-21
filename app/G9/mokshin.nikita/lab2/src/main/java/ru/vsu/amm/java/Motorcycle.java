package ru.vsu.amm.java;

public class Motorcycle extends Vehicle {

    public Motorcycle(String brand, String model, double rentalPricePerDay) {
        super(brand, model, rentalPricePerDay);
    }

    @Override
    public String toString() {
        return String.format("Motorcycle(brand=%s, model=%s, rentalPricePerDay=%.1f)", getBrand(), getModel(), getRentalPricePerDay());
    }
}
