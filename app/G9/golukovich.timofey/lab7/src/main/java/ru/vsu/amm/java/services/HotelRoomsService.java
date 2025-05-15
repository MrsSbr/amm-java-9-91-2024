package ru.vsu.amm.java.services;

import ru.vsu.amm.java.dtos.HotelRoomDto;

import java.util.List;

public interface HotelRoomsService {
    List<HotelRoomDto> getHotelRoomsByHotelId(int hotelId);
}
