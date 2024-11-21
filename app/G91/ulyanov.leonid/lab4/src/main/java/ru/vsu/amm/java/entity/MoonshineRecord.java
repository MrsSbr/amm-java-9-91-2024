package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.Ingredients;

import java.time.LocalDate;
import java.util.List;

public record MoonshineRecord(LocalDate date, String label, List<Ingredients> ingredients,
                              float volume, float makingTime) {
}
