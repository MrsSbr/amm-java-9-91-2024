package ru.vsu.amm.java.requests;

public record RegisterRequest(
        String firstName,
        String lastName,
        String patronymic,
        String city,
        String email,
        String phoneNumber,
        String password) {
}
