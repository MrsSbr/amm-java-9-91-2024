package ru.vsu.amm.java.entity;


public record Response(String value) {

    @Override
    public String toString() {
        return this.value;
    }
}
