package ru.vsu.amm.java;

import java.util.Objects;

public class ConcreteMixer extends HeavyMachine {

    private final int volume;

    public ConcreteMixer(String brand, int weight, int volume) {
        super(brand, weight);
        this.volume = volume;
    }

    @Override
    public String toString() {
        return "ConcreteMixer " + "\u001B[31m" + brand + "\u001B[0m" + " is working! " + " Volume: " + "\u001B[31m" + volume + "\u001B[0m" + " cubic meters";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj) || getClass() != obj.getClass())
            return false;
        ConcreteMixer other = (ConcreteMixer) obj;
        return volume == other.volume;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), volume);
    }

    @Override
    public void working() {
        System.out.println("\u001B[31m" + brand + "\u001B[0m" + " is mixing ");
    }
}
