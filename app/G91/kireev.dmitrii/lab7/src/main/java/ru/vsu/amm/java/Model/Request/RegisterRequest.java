package ru.vsu.amm.java.Model.Request;

public record RegisterRequest(String name, String email, String password, String phone) {
}
