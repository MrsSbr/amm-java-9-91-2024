package ru.vsu.amm.java.Dtos;

import ru.vsu.amm.java.Enums.Role;

import java.time.LocalDate;

public record UserDto(
        int id,
        String fullName,
        LocalDate birthday,
        String eMail,
        String numberPhone,
        Role userRole) {
}