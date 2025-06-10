package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entity.UserEntity;

public interface AuthenticationService {

    UserEntity login(String email, String hashPassword);

    UserEntity register(String email, String hashPassword);
}
