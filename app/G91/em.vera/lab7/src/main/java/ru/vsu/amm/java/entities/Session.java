package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Session {
    private Long idSession;
    private Long psychologist_id;
    private Long client_id;
    private LocalDate date;
    private BigDecimal price;
    private Short duration;
}
