package ru.vsu.amm.java;

import java.util.Objects;

public abstract class Fish implements WaterFowlable {
    private String name;
    private String color;

    public Fish(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    public abstract void speak();

    @Override
    public void swim() {

        System.out.println(name + " is swimming!");
    }

    @Override
    public String toString() {
        return "My name is " + name + ". My color is " + color + '.';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fish fish = (Fish) o;
        return Objects.equals(name, fish.name) && Objects.equals(color, fish.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color);
    }
}

