package ru.vsu.amm.java;

import java.util.Objects;

public class Chicken extends FarmAnimal {
    private final int eggProduction;

    public Chicken(String name, int eggProduction) {
        super(name, "Chicken");
        this.eggProduction = eggProduction;
    }

    @Override
    public void eat() {
        System.out.println(getName() + " eats grains");
    }

    @Override
    public String toString() {
        return super.toString() + ", egg production: " + eggProduction + " eggs";
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Chicken other = (Chicken) obj;
        return eggProduction == other.eggProduction;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), eggProduction);
    }
}