package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.Subjects;

import java.time.Year;
import java.util.List;

public record OlympiadRecord(Year year, Subjects subject, int schoolClass, List<String> winners) {
}
