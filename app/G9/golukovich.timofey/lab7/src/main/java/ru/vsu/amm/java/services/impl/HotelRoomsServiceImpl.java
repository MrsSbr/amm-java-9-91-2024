package ru.vsu.amm.java.services.impl;

import ru.vsu.amm.java.dtos.HotelRoomDto;
import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.mappers.HotelRoomDtoMapper;
import ru.vsu.amm.java.repository.HotelRoomRepo;
import ru.vsu.amm.java.services.HotelRoomsService;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class HotelRoomsServiceImpl implements HotelRoomsService {
    private static final Logger logger = Logger.getLogger(HotelRoomsServiceImpl.class.getName());

    private final HotelRoomRepo hotelRoomRepo;

    public HotelRoomsServiceImpl() {
        logger.info("EmployeesServiceImpl configuring");
        hotelRoomRepo = new HotelRoomRepo();
    }

    @Override
    public List<HotelRoomDto> getHotelRoomsByHotelId(int hotelId) {
        logger.info("get hotel rooms by hotel id");

        try {
            return hotelRoomRepo.getAllByHotelId(hotelId).stream()
                    .map(HotelRoomDtoMapper::mapFromEntity)
                    .toList();
        } catch (SQLException e) {
            logger.severe(e.getMessage());
            throw new DatabaseException(e.getMessage());
        }
    }
}
