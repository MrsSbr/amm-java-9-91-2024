package ru.vsu.amm.java;

import java.util.Objects;

public abstract class Vehicle implements Rentable {
    private String brand;
    private String model;
    private double rentalPricePerDay;
    private boolean isRented;

    public Vehicle(String brand, String model, double rentalPricePerDay) {
        this.brand = brand;
        this.model = model;
        this.rentalPricePerDay = rentalPricePerDay;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public double getRentalPricePerDay() {
        return rentalPricePerDay;
    }

    public void setRentalPricePerDay(double rentalPricePerDay) {
        this.rentalPricePerDay = rentalPricePerDay;
    }

    public double calculateRentalCost(int days) {
        return days * rentalPricePerDay;
    }

    @Override
    public void rent() {

        if (isRented) {
            System.out.println(brand + " " + model + " is rented already.");
        } else {
            isRented = true;
            System.out.println(brand + " " + model + " is rented.");
        }
    }

    @Override
    public void returnBack() {
        if(isRented) {
            isRented = false;
            System.out.println(brand + " " + model + " is returned.");
        } else {
            System.out.println(brand + " " + model + " is returned already.");
        }
    }

    @Override
    public String toString() {
        return String.format("Vehicle(brand=%s, model=%s, rentalPricePerDay=%.2f)", brand, model, rentalPricePerDay);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Vehicle other = (Vehicle) obj;
        return brand.equals(other.brand) && model.equals(other.model) && rentalPricePerDay == other.rentalPricePerDay;
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, model, rentalPricePerDay);
    }
}
