package ru.vsu.amm.java;

import java.util.Objects;

public class Digger extends ConstructionMachineImpl {

    public final int POWER;

    public Digger(String brand, int weight, int POWER) {
        super(brand, weight);
        this.POWER = POWER;
    }

    @Override
    public String toString() {
        return "Digger " + "\u001B[31m" + BRAND + "\u001B[0m" + " is working!" + " Power: " + "\u001B[31m" + POWER + "\u001B[0m" + " watt";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj) || getClass() != obj.getClass())
            return false;
        Digger other = (Digger) obj;
        return POWER == other.POWER;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), POWER);
    }

    @Override
    public void working() {
        System.out.println("Digger " + "\u001B[31m" + BRAND + "\u001B[0m" + " is digging");
    }
}
