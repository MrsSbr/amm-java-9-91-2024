package ru.vsu.amm.java;

import java.util.Objects;

public abstract class HeavyMachine implements ConstructionMachine {

    public final int weight;
    public final String brand;

    public HeavyMachine(String brand, int weight) {
        this.brand = brand;
        this.weight = weight;
    }

    @Override
    public void startEngine() {
        System.out.println("brrrr, " + "\u001B[31m" + brand + "\u001B[0m" + " started his engine!");
    }

    @Override
    public void working() {
        System.out.println("Abstract machine " + "\u001B[31m" + brand + "\u001B[0m" + " is working");
    }

    @Override
    public void stopEngine() {
        System.out.println("stopped engine of " + "\u001B[31m" + brand + "\u001B[0m");
    }

    @Override
    public String toString() {
        return "Abstract machine, brand: " + "\u001B[31m" + brand + "\u001B[0m" + " weight: " + "\u001B[31m" + weight + "\u001B[0m";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        HeavyMachine other = (HeavyMachine) obj;
        return brand.equals(other.brand) && weight == other.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, weight);
    }
}
