package ru.vsu.amm.java.requests;

public record RegisterRequest(String email,
                              String password,
                              String lastName,
                              String firstName,
                              String patronymicName,
                              String phoneNumber) {
}
