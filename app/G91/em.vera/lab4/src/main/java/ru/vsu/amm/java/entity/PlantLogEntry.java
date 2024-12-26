package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.Fertilizer;

import java.time.LocalDate;

public record PlantLogEntry(String name, double waterAmount, LocalDate date, Fertilizer fertilizer) {
}