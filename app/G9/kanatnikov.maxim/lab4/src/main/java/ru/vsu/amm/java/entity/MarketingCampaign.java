package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.Type;

import java.time.LocalDate;

public record MarketingCampaign(LocalDate dateStart, LocalDate dateEnd, Type type, int coverage, int budget) {
}
