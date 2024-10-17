package ru.vsu.amm.java;

import java.util.Objects;

public class ScrewingTool extends Tool implements Screwable {
    private String driveType;

    public ScrewingTool(String name, double width, double length, String brand, boolean isReadyToWork, String driveType) {
        super(name, width, length, brand, isReadyToWork);
        this.driveType = driveType;
    }

    public ScrewingTool() {
    }

    @Override
    public void screw() {
        System.out.println("Закрутил");
    }

    @Override
    public void use() {
        System.out.println("Инструмент " + getName() + " с типом привода '" + driveType + "' начинает крутиться...");
    }

    @Override
    public String toString() {
        return "Завинчивающий инструмент " + super.toString() +
                "\nс приводом типа: " + driveType;
    }

    @Override
    public boolean equals(Object o) {
        ScrewingTool that = (ScrewingTool) o;
        return driveType.equals(that.driveType) && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), driveType);
    }
}