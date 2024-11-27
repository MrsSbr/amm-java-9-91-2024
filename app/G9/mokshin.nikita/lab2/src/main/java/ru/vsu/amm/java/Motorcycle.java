package ru.vsu.amm.java;

import java.util.Objects;

public class Motorcycle extends Vehicle {
    private int helmetCount;

    public Motorcycle(String brand, String model, double rentalPricePerDay, int helmetCount) {
        super(brand, model, rentalPricePerDay);
        this.helmetCount = helmetCount;
    }

    public int getHelmetCount() {
        return helmetCount;
    }

    public void setHelmetCount(int helmetCount) {
        this.helmetCount = helmetCount;
    }

    @Override
    public String toString() {
        return String.format("Motorcycle(brand=%s, model=%s, helmetCount=%d rentalPricePerDay=%.1f)", getBrand(), getModel(), getHelmetCount(), getRentalPricePerDay());
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && this.helmetCount == ((Motorcycle) obj).helmetCount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getBrand(), getModel(), getHelmetCount(), getRentalPricePerDay());
    }
}
