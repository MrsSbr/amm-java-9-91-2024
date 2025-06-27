package ru.vsu.amm.java.Dtos;

import ru.vsu.amm.java.Enums.Language;

public record TourDto(
        int id,
        int idGuide,
        String title,
        String description,
        double duration,
        int price,
        int maxParticipants,
        String startLocation,
        Language languages) {
}
