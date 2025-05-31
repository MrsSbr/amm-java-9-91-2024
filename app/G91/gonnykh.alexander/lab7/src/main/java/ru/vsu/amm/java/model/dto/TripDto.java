package ru.vsu.amm.java.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripDto {
    private Long id;
    private String carModel;
    private String carManufacturer;
    private LocalDateTime startTrip;
    private LocalDateTime endTrip;
    private BigDecimal finalPrice;
}
