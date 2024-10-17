package ru.vsu.amm.java.entity;


public record Driver(String name, int age) {

    @Override
    public String toString() {
        return "Водитель: " + name()
                + "\nВозраст: " + age();
    }
}
