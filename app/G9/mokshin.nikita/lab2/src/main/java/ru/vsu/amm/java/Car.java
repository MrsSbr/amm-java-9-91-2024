package ru.vsu.amm.java;

public class Car extends Vehicle {
    private int seatingCapacity;

    public Car(String brand, String model, double rentalPricePerDay, int seatingCapacity) {
        super(brand, model, rentalPricePerDay);
        this.seatingCapacity = seatingCapacity;
    }

    @Override
    public double calculateRentalCost(int days) {
        if(days > 7) {
            return days * getRentalPricePerDay() * 0.9;
        } else {
            return days * getRentalPricePerDay();
        }
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    @Override
    public String toString() {
        return String.format("Car(brand=%s, model=%s, rentalPricePerDay=%.2f, seatingCapacity=%s)", getBrand(), getModel(), getRentalPricePerDay(), seatingCapacity);
    }
}
