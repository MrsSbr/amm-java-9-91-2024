package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class FlightEntity {
    private Long flightId;
    private String origin;
    private String destination;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    private String airplaneModel;
    private Short capacity;
    private BigDecimal price;
}
