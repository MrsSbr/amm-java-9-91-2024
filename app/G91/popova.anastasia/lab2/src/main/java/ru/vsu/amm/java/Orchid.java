package ru.vsu.amm.java;

import java.util.Objects;

public class Orchid extends HousePlant {
    private final String color;

    public String getColor() { return color; }

    public Orchid( String name,
                   int height,
                   final int maxHeight,
                   boolean hasFlowers,
                   String color) {
        super(name, height, maxHeight, hasFlowers);
        this.color = color;
    }

    @Override
    public String toString() {
        return "*Orchid*\n"
                + super.toString()
                + "\nPlant color: \t" + color;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj)
                && color == ((Orchid) obj).color;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(color);
    }
}