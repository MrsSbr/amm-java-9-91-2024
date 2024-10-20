package ru.vsu.amm.java;

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
