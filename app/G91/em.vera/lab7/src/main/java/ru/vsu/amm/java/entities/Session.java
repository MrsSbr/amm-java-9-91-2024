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
    private Psychologist psychologist;
    private Client client;
    private LocalDate date;
    private BigDecimal price;
    private Short duration;
}
