package ru.vsu.amm.java.services;

import ru.vsu.amm.java.dtos.HotelDto;

import java.util.List;
import java.util.Optional;

public interface HotelsService {
    HotelDto getHotelById(Integer hotelId);
    List<HotelDto> getAllHotels();
}
