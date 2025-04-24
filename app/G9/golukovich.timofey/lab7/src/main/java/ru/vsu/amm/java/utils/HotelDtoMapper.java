package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.dtos.HotelDto;
import ru.vsu.amm.java.entities.HotelEntity;

public class HotelDtoMapper {
    public static HotelDto MapFromEntity(HotelEntity e) {
        return new HotelDto(e.getId(), e.getName(), e.getAddress(),
                e.getEmail(), e.getPhoneNumber(), e.getOpeningDate());
    }
}
