package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.Fatality;
import ru.vsu.amm.java.enums.Hero;

import java.time.LocalDate;

public record Fight(Integer tournamentNum,
                    LocalDate date,
                    Hero participant1,
                    Hero participant2,
                    Hero winner,
                    Fatality fatality) {
}
