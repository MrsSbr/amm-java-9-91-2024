package ru.vsu.amm.java.model.requests;

public record RegisterRequest(String name, String email, String password) {
}