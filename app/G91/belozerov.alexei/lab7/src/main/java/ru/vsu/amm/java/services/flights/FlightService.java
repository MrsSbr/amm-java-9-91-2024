package ru.vsu.amm.java.services.flights;

import ru.vsu.amm.java.models.dto.FlightDto;

import java.util.List;

public interface FlightService {

    List<FlightDto> getAllFlights();
}
