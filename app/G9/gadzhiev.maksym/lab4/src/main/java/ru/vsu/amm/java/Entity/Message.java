package ru.vsu.amm.java.Entity;

import java.time.LocalDate;

public record Message(LocalDate date, String fio, String text) {
}

