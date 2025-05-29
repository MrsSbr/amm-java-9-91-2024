package ru.vsu.amm.java.dto;

import java.time.LocalDateTime;

public record TripEndDto(Long tripId, Long scooterId, String model, LocalDateTime startTime) {
}
