package ru.vsu.amm.java.requests;

import ru.vsu.amm.java.enums.Role;

public record RegisterRequest(String lastName,
                              String firstName,
                              String patronymic,
                              String login,
                              String password,
                              Role role){
}
