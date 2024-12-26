package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.BirdType;

import java.time.LocalDate;

public record EggProductionRecord(LocalDate date, BirdType birdType, int eggsCount) {
}
