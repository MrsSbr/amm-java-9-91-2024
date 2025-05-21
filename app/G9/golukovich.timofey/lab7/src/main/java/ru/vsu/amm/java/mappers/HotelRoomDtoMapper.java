package ru.vsu.amm.java.mappers;

import ru.vsu.amm.java.dtos.HotelRoomDto;
import ru.vsu.amm.java.entities.HotelRoomEntity;

public class HotelRoomDtoMapper {
    public static HotelRoomDto mapFromEntity(HotelRoomEntity e) {
        return new HotelRoomDto(e.getId(), e.getHotelId(), e.getRoomNumber(), e.getFloorNumber(), e.getBedsCount(), e.getSpecifications());
    }
}