package ru.vsu.amm.java.Requests;

import java.time.LocalDate;

public record RegisterRequest(
        String surname,
        String name,
        String patronymicname,
        String phoneNumber,
        String email,
        String password
) {
}
