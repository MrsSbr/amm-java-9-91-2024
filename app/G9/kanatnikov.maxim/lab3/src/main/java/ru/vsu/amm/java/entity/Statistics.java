package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.Article;
import ru.vsu.amm.java.enums.Name;
import ru.vsu.amm.java.enums.Result;

import java.time.LocalDate;

public record Statistics(Name defendantName, Name plaintiffName, LocalDate date, Article article, Result result) {
}
