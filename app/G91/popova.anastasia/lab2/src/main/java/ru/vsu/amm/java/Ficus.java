package ru.vsu.amm.java;

import java.util.Objects;

public class Ficus extends HousePlant {
    private final int leafSize;

    public int getLeafSize() { return leafSize; }

    public Ficus (String name,
                  int height,
                  int maxHeight,
                  boolean hasFlowers,
                  int leafSize) {
        super(name, height, maxHeight, hasFlowers);
        this.leafSize = leafSize;
    }

    @Override
    public String toString() {
        return "*Ficus*\n"
                + super.toString()
                + "\nPlant leaf size: \t" + leafSize;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && ((Ficus) obj).leafSize == leafSize;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(leafSize);
    }
}