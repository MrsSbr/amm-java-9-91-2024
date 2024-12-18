package ru.vsu.amm.java;

import java.time.LocalDate;

public record DayStatistic(LocalDate date, double feedConsumed, double milkProduced) {
}
