package ru.vsu.amm.java;

import java.util.Objects;

public class HammerTool extends Tool {
    private double weight;

    public HammerTool(String name, double width, double length, String brand, boolean isReadyToWork, double weight) {
        super(name, width, length, brand, isReadyToWork);
        this.weight = weight;
    }

    public HammerTool() {
    }

    @Override
    public void use() {
        System.out.println("Инструмент " + getName() + " с весом" + weight + "гр. начинает стучать...");
    }

    @Override
    public String toString() {
        return "Забивающий инструмент " + super.toString() +
                "\nвес: " + weight;
    }

    @Override
    public boolean equals(Object o) {
        HammerTool that = (HammerTool) o;
        return Double.compare(weight, that.weight) == 0 && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), weight);
    }
}
