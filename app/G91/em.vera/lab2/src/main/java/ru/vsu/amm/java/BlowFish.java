package ru.vsu.amm.java;

public class BlowFish extends Fish {
    private int diameter;

    public BlowFish(String name, int diameter) {
        super(name, "yellow");
        this.diameter = diameter;
    }

    public int getDiameter() {
        return diameter;
    }

    @Override
    public void speak() {
        System.out.println("I am a blow fish!");
    }

    @Override
    public String toString() {
        return super.toString() + " My diameter is " + diameter + '.';
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && ((BlowFish) obj).diameter == diameter;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + diameter;
    }
}