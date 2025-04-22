package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class TripEntity {
    private Long id;
    private Long userId;
    private Long scooterId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private Double startLatitude;
    private Double startLongitude;

    private Double endLatitude;
    private Double endLongitude;
}
