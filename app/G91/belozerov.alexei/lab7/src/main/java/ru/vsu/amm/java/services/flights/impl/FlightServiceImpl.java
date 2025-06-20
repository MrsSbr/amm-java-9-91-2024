package ru.vsu.amm.java.services.flights.impl;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.converters.FlightConverter;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.models.dto.FlightDto;
import ru.vsu.amm.java.repositories.impl.FlightRepository;
import ru.vsu.amm.java.services.flights.FlightService;

import java.sql.SQLException;
import java.util.List;

@Slf4j
public class FlightServiceImpl implements FlightService {

    private final FlightRepository flightRepository;

    public FlightServiceImpl(){
        flightRepository = new FlightRepository();
    }

    @Override
    public List<FlightDto> getAllFlights() {
        try {
            return flightRepository.findAll().stream()
                    .map(FlightConverter::toDto)
                    .toList();
        } catch (SQLException e) {
            log.error("Error fetching all flights", e);
            throw new DataAccessException("Failed to fetch flights", e);
        }
    }
}
