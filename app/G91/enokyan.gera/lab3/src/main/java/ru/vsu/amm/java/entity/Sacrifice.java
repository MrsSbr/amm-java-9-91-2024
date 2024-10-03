package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.SacrificeType;

import java.time.LocalDate;

public record Sacrifice (LocalDate date, SacrificeType sacrificeType, int daysUntilRain) {
}
