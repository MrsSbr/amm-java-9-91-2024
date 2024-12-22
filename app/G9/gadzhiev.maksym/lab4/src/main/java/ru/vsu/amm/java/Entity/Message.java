package ru.vsu.amm.java.Entity;

import java.time.LocalDate;
import java.util.Date;

public record Message(LocalDate date, String fio, String text) {}
