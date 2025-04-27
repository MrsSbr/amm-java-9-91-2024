package ru.vsu.amm.java.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class SessionDto {
    private Long idSession;
    private LocalDate date;
    private BigDecimal price;
    private Short duration;
}
