package ru.vsu.amm.java;

import java.util.Objects;

public class Digger extends HeavyMachine {

    private final int power;

    public Digger(String brand, int weight, int power) {
        super(brand, weight);
        this.power = power;
    }

    @Override
    public String toString() {
        return "Digger " + "\u001B[31m" + brand + "\u001B[0m" + " is working!" + " Power: " + "\u001B[31m" + power + "\u001B[0m" + " watt";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj) || getClass() != obj.getClass())
            return false;
        Digger other = (Digger) obj;
        return power == other.power;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), power);
    }

    @Override
    public void working() {
        System.out.println("Digger " + "\u001B[31m" + brand + "\u001B[0m" + " is digging");
    }
}
