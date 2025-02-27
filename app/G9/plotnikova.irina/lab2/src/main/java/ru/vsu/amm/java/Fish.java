package ru.vsu.amm.java;
 import java.util.Objects;

public abstract class Fish implements SwimBehavior {
    protected String name;
    protected double weight;

    public Fish(String name, double weight) {
        this.name = name;
        this.weight = weight;
    }

    public String getName() {
        return name;
    }


    public  abstract void  eat();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fish fish = (Fish) o;
        return Double.compare(fish.weight, weight) == 0 && name.equals(fish.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, weight);
    }

    @Override
    public String toString() {
        return "Рыба" + name + ", вес: " + weight + " кг";
    }

    public String getDescription() {
        return name + " - это рыба, живущая в воде.";
    }

}
