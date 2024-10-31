package ru.vsu.amm.java;

import java.util.Objects;

public abstract class AbstractCosmetic implements Cosmetic {
    private String brand;

    private double price;

    public AbstractCosmetic(String brand, double price) {
        this.brand=brand;
        this.price=price;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getBrand() {
        return brand;
    }


    public void setPrice(double price) {
        this.price = price;
    }


    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Brand: " + brand + " Price: "+ price;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj)
            return true;
        if(obj == null || getClass() != obj.getClass())
            return false;
        AbstractCosmetic that = (AbstractCosmetic) obj;
        return that.getBrand().equals(this.getBrand()) && this.getPrice() == that.getPrice();
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, price);
    }


}
