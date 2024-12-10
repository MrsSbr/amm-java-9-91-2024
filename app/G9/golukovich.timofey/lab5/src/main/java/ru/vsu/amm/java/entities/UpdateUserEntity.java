package ru.vsu.amm.java.entities;

import java.time.LocalDate;

public record UpdateUserEntity(String userName, String password, LocalDate creationTime) {
}
