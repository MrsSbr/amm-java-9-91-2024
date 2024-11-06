package ru.vsu.amm.java;

import java.util.Objects;

public abstract class HousePlant implements Plant {

    private String name;
    private int height;
    private int maxHeight;
    private boolean hasFlowers;

    public HousePlant() {
    }

    public HousePlant(String name, int height, int maxHeight, boolean hasFlowers) {
        this.name = name;
        this.height = height;
        this.maxHeight = maxHeight;
        this.hasFlowers = hasFlowers;
    }

    public String getName() { return name; }

    public int getHeight() { return height; }

    public int getMaxHeight() { return maxHeight; }

    public boolean getFlowers() { return hasFlowers; }

    public void setHeight(int height) {
        this.height = height;
    }

    public void grow() {
        System.out.println("Initial height: \t" + height);
        if (height < maxHeight) {
            height += 1;
            System.out.println("~ plant is growing! ~");
            System.out.println("Current height: \t" + height);
        }
        else System.out.println("oops! \t has reached its maximum height!\n" + name);
    }

    public void bloom() {
        if (hasFlowers) {
            System.out.println("\n~ plant is blooming! ~\n");
        }
        else System.out.println("oops! this plant can't have flowers!\n");
    }

    @Override
    public String toString() {
        return "Plant name: \t" + name
                + "\nPlant height: \t" + height
                + "\nPlant max height: \t" + maxHeight;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        HousePlant other = (HousePlant) obj;
        return other.getName().equals(this.getName())
                && other.getHeight() == this.getHeight()
                && other.getMaxHeight() == this.getMaxHeight();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, height, maxHeight);
    }
}