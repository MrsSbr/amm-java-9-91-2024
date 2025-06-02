package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.dtos.HotelDto;
import ru.vsu.amm.java.entities.HotelEntity;

public class HotelDtoMapper {
    public static HotelDto mapFromEntity(HotelEntity e) {
        return new HotelDto(e.getId(), e.getName(), e.getAddress(),
                e.getEmail(), e.getPhoneNumber(), e.getOpeningDate());
    }
}
