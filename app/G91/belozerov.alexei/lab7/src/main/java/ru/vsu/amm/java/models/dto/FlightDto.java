package ru.vsu.amm.java.models.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record FlightDto(
        Long flightId,
        String origin,
        String destination,
        LocalDateTime departureTime,
        LocalDateTime arrivalTime,
        String airplaneModel,
        Short capacity,
        BigDecimal price
) {
}
