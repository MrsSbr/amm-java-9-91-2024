package ru.vsu.amm.java;

import java.util.Objects;

public abstract class ConstructionMachineImpl implements ConstructionMachine {

    public final int WEIGHT;
    public final String brand;

    public ConstructionMachineImpl(String brand, int weight) {
        this.brand = brand;
        this.WEIGHT = weight;
    }

    @Override
    public void startEngine() {
        System.out.println("brrrr, " + "\u001B[31m" + brand + "\u001B[0m" + " started his engine!");
    }

    @Override
    public void stopEngine() {
        System.out.println("stopped engine of " + "\u001B[31m" + brand + "\u001B[0m");
    }

    @Override
    public String toString() {
        return "Abstract machine, brand: " + "\u001B[31m" + brand + "\u001B[0m" + " weight: " + "\u001B[31m" + WEIGHT + "\u001B[0m";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        ConstructionMachineImpl other = (ConstructionMachineImpl) obj;
        return brand.equals(other.brand) && WEIGHT == other.WEIGHT;
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, WEIGHT);
    }
}
