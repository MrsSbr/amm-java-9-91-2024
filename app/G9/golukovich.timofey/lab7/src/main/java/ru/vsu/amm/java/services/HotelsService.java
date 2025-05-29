package ru.vsu.amm.java.services;

import ru.vsu.amm.java.dtos.HotelDto;

import java.util.List;

public interface HotelsService {
    HotelDto getHotelById(Integer hotelId, boolean isForUpdate);
    List<HotelDto> getAllHotels(boolean isForUpdate);
}
