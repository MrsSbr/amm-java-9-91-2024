package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserCarEntity {
    private Long id;
    private Long userId;
    private Long carId;
    private LocalDateTime startTrip;
    private Integer duration;
    private BigDecimal priceForMinute;

}