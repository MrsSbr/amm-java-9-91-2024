package ru.vsu.amm.java.model.requests;

import ru.vsu.amm.java.enums.Role;

public record RegisterRequest(String name, String email, String password, Role role) {
}