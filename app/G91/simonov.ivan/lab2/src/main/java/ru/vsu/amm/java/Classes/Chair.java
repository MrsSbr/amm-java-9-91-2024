package ru.vsu.amm.java.Classes;

import java.util.Objects;

public class Chair extends FurnitureImpl {

    private final int legs;

    public Chair(String name,
          String material,
          double price,
          boolean isShowcaseSample,
          int legs) {
        super(name, material, price, isShowcaseSample);
        this.legs = legs;
    }

    @Override
    public void move() {
        System.out.println("Стул " + name + " перемещается со скрежетом ножек!");
    }

    @Override
    public String toString() {
        return "Стул\n" +
                super.toString() +
                "\nКол-во ножек: " + legs;
    }

    @Override
    public boolean equals(Object obj) {
        Chair other = (Chair) obj;
        return legs == other.legs && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), legs);
    }
}
