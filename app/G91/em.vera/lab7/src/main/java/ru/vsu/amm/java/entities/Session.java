package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Session {
    private Long idSession;
    private Long idPsychologist;
    private Long idClient;
    private LocalDate date;
    private BigDecimal price;
    private Short duration;
}
