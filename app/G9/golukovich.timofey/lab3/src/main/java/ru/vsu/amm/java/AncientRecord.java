package ru.vsu.amm.java;


import java.time.LocalDate;

public record AncientRecord(LocalDate date, int mammothWeight, HunterName hunterName) {
    public AncientRecord(LocalDate date, int mammothWeight, HunterName hunterName) {
        this.date = date;
        this.mammothWeight = mammothWeight;
        this.hunterName = hunterName;
    }

    public LocalDate getDate() {
        return date;
    }

    public HunterName getHunterName() {
        return hunterName;
    }

    public int getMammothWeight() {
        return mammothWeight;
    }
}
