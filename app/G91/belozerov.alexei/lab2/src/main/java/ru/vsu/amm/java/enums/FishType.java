package ru.vsu.amm.java.enums;

public enum FishType {
    COD("Треска"), SALMON("Лосось"), SARDINES("Сардина");

    private final String name;

    FishType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
