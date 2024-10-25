package ru.vsu.amm.java;

import java.util.Objects;

public abstract class AbstractCosmetic implements Cosmetic {
    protected String brand;
    protected double price;

    public AbstractCosmetic(String brand, double price) {
        this.brand=brand;
        this.price=price;
    }

    @Override
    public String getBrand() {
        return brand;
    }

    @Override
    public double getPrice() {
        return price;
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

    @Override
    public String toString() {
        return "Brand: " + brand + " Price: "+ price;
    }
}
