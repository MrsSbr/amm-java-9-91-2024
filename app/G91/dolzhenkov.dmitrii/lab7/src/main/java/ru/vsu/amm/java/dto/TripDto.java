package ru.vsu.amm.java.dto;

import java.time.LocalDateTime;

public record TripDto(Long tripId, Long scooterId, String model, LocalDateTime startTime, LocalDateTime endTime, Double distance){
}
