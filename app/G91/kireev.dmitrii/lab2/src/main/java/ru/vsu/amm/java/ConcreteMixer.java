package ru.vsu.amm.java;

import java.util.Objects;

public class ConcreteMixer extends ConstructionMachineImpl {

    private final int VOLUME;

    public ConcreteMixer(String brand, int weight, int VOLUME) {
        super(brand, weight);
        this.VOLUME = VOLUME;
    }

    @Override
    public String toString() {
        return "ConcreteMixer " + "\u001B[31m" + brand + "\u001B[0m" + " is working! " + " Volume: " + "\u001B[31m" + VOLUME + "\u001B[0m" + " cubic meters";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj) || getClass() != obj.getClass())
            return false;
        ConcreteMixer other = (ConcreteMixer) obj;
        return VOLUME == other.VOLUME;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), VOLUME);
    }

    @Override
    public void working() {
        System.out.println("\u001B[31m" + brand + "\u001B[0m" + " is mixing ");
    }
}
