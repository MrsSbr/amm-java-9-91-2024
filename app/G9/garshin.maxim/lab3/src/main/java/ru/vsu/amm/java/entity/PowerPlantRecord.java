package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.Type;

import java.time.Instant;
import java.time.LocalDate;

public class PowerPlantRecord {
    private LocalDate date;

    private Type type;

    private int power;

    public PowerPlantRecord() { }

    public PowerPlantRecord(LocalDate date, Type type, int power) {
        this.date = date;
        this.type = type;
        this.power = power;
    }

    public LocalDate getDate() {
        return date;
    }

    public Type getType() {
        return type;
    }

    public int getPower() {
        return power;
    }
}