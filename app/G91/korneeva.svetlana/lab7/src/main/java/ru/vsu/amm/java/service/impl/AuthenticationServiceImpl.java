package ru.vsu.amm.java.service.impl;

import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.exception.AuthException;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.AuthenticationService;

import java.sql.SQLException;

public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;

    public AuthenticationServiceImpl() {
        userRepository = new UserRepository();
    }

    @Override
    public void login(String email, String hashPassword) {
        try {
            UserEntity user = userRepository.findByEmail(email).orElseThrow(
                    () -> new AuthException("Такого пользователя не существует")
            );
            // TODO check password
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void register(String email, String hashPassword) {
        try {
            if (userRepository.findByEmail(email).isEmpty()) {
                UserEntity user = new UserEntity(null, hashPassword, email); // TODO hashing password
                userRepository.save(user);
            } else {
                throw new AuthException("Логин занят");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
