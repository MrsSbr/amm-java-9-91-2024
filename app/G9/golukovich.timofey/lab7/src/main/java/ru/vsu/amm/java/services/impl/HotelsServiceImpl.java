package ru.vsu.amm.java.services.impl;

import ru.vsu.amm.java.dtos.HotelDto;
import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.exceptions.HotelNotFoundException;
import ru.vsu.amm.java.repository.HotelRepo;
import ru.vsu.amm.java.services.HotelsService;
import ru.vsu.amm.java.mappers.HotelDtoMapper;

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
    public HotelDto getHotelById(Integer hotelId, boolean isForUpdate) {
        try {
            var hotelOpt = hotelRepo.getById(hotelId, isForUpdate);
            if (hotelOpt.isPresent()) {
                return HotelDtoMapper.mapFromEntity(hotelOpt.get());
            } else {
                final String message = "Hotel " + hotelId + " does not exists";
                logger.info(message);
                throw new HotelNotFoundException(message);
            }
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public List<HotelDto> getAllHotels(boolean isForUpdate) {
        logger.info("get all hotels");

        try {
            return hotelRepo.getAll(isForUpdate).stream()
                    .map(HotelDtoMapper::mapFromEntity)
                    .toList();
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
