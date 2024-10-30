package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.interfaces.Shippable;

import java.util.Objects;

public abstract class Vessel implements Shippable {
    private final String name;
    private final int capacity;

    public Vessel(String name, int capacity) {
        this.name = name;
        this.capacity = capacity;
    }

    @Override
    public void sail() {
        System.out.println(name + " начинает плавание.");
    }

    @Override
    public void stop() {
        System.out.println(name + " останавливается.");
    }

    public int getCapacity() {
        return capacity;
    }

    public String getName() {
        return name;
    }

    public abstract String getVesselType();

    @Override
    public String toString() {
        return "Название судна: " + name
                + "\nВместимость: " + capacity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Vessel vessel = (Vessel) o;
        return capacity == vessel.capacity && name.equals(vessel.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, capacity);
    }
}
