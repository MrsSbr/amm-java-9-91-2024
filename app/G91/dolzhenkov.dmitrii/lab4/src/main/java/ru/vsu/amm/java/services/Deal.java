package ru.vsu.amm.java.services;

import java.time.LocalDate;

public record Deal(String manager, String client, double amount, LocalDate date) {
}
