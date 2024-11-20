package ru.vsu.amm.java;

import java.util.Objects;

public class Cow extends FarmAnimal {
    private final double milkProduction;

    public Cow(String name, double milkProduction) {
        super(name, AnimalType.Mammal);
        this.milkProduction = milkProduction;
    }

    @Override
    public void eat() {
        System.out.println(getName() + " eats grass");
    }

    @Override
    public String toString() {
        return super.toString() + ", milk production: " + milkProduction + " liters";
    }

    @Override
    public boolean equals(Object obj) {
        if (!super.equals(obj)) {
            return false;
        }
        Cow other = (Cow) obj;
        return Double.compare(milkProduction, other.milkProduction) == 0;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), milkProduction);
    }
}