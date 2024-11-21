package ru.vsu.amm.java.entity;

import ru.vsu.amm.java.enums.Fatality;
import ru.vsu.amm.java.enums.Hero;

public record Fight(int tournamentNum, Hero participant1, Hero participant2, Hero winner, Fatality fatality) {
}
