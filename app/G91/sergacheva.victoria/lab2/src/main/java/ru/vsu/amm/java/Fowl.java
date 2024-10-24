package ru.vsu.amm.java;

import java.util.Objects;

public abstract class Fowl implements Behavior {
    private String name;

    private String color;

    public Fowl(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Bird: " + name + ", color: " + color;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Fowl other = (Fowl) obj;
        return name.equals(other.name) && color.equals(other.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color);    }
}
