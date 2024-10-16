package ru.vsu.amm.java;

import java.util.Objects;

public class ScrewingInstrument extends InstrumentImpl {
    private String driveType;

    public ScrewingInstrument(String name, double width, double length, String brand, boolean isReadyToWork, String driveType) {
        super(name, width, length, brand, isReadyToWork);
        this.driveType = driveType;
    }

    public ScrewingInstrument() { }

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
        ScrewingInstrument that = (ScrewingInstrument) o;
        return driveType.equals(that.driveType) && super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), driveType);
    }
}