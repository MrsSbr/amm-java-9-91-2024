package ru.vsu.amm.java.entity;

public record City(String name) {

    @Override
    public String toString() {
        return this.name;
    }
}
