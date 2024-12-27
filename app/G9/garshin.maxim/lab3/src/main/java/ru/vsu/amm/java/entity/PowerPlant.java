package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.Type;

import java.time.LocalDate;

public record PowerPlant(LocalDate date, Type type, int power) {
}