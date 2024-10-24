package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.Genre;
import ru.vsu.amm.java.enums.Rating;

import java.time.LocalDate;

public record GameWalkthrough(String name, Genre genre, LocalDate date, int time, Rating rating) {
}
