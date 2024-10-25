package ru.vsu.amm.java.enums;

public enum CargoType {
    CONTAINER("Контейнер"), OIL("Нефть"), GAS("Газ");

    private final String name;

    CargoType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
