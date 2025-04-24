package ru.vsu.amm.java.services.impl;

import ru.vsu.amm.java.dtos.HotelDto;
import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.repository.HotelRepo;
import ru.vsu.amm.java.services.HotelsService;
import ru.vsu.amm.java.utils.HotelDtoMapper;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class HotelsServiceImpl implements HotelsService {
    private static final Logger logger = Logger.getLogger(HotelsServiceImpl.class.getName());
    private final HotelRepo hotelRepo;

    public HotelsServiceImpl() {
        logger.info("HotelsServiceImpl configuring");
        hotelRepo = new HotelRepo();
    }
    @Override
    public List<HotelDto> GetAllHotels() {
        logger.info("get all hotels");

        try {
            return hotelRepo.getAll().stream()
                    .map(HotelDtoMapper::MapFromEntity)
                    .toList();
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
