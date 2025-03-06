package ru.vsu.amm.java;

import java.util.Objects;

public class GoldenFish extends AquariumFish {

    private int weight;

    public GoldenFish(String name, int age, int weight) {
        super(name, age);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public void jump() {
        System.out.println("Golden fish " + getName() + " is jumping");
    }

    @Override
    public String toString() {
        return super.toString() + ", " + weight;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && weight == ((GoldenFish) o).getWeight();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAge(), getWeight());
    }
}