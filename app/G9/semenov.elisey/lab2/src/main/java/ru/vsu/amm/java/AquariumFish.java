package ru.vsu.amm.java;

import java.util.Objects;

public abstract class AquariumFish implements Fish {
    private String name;

    private int age;

    public AquariumFish(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    @Override
    public void swim() {
        System.out.println("Fish " + name + " is swimming");
    }

    public abstract void jump();

    @Override
    public String toString() {
        return "Fish: " + name + ", " + age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AquariumFish fish = (AquariumFish) o;
        return name.equals(fish.getName()) && age == fish.getAge();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}