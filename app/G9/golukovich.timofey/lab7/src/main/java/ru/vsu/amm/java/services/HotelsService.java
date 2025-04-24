package ru.vsu.amm.java.services;

import ru.vsu.amm.java.dtos.HotelDto;

import java.util.List;

public interface HotelsService {
    List<HotelDto> GetAllHotels();
}
