package ru.vsu.amm.java.models.requests;

public record ClientRegisterRequest(String name, String email, String password) {
}
