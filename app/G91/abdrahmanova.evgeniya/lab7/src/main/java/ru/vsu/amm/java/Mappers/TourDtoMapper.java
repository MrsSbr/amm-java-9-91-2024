package ru.vsu.amm.java.Mappers;

import ru.vsu.amm.java.Dtos.TourDto;
import ru.vsu.amm.java.Entities.Tour;

public class TourDtoMapper {
    public static TourDto mapFromEntity(Tour e) {
        var languages = e.getLanguages().name();
        languages = languages.substring(0, 1).toUpperCase() + languages.substring(1).toLowerCase();
        return new TourDto(e.getId(), e.getIdGuide(), e.getTitle(), e.getDescription(),
                e.getDuration(), e.getPrice(), e.getMaxParticipants(), e.getStartLocation(), e.getLanguages());
    }
}
