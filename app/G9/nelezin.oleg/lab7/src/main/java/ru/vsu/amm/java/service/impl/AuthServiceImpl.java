package ru.vsu.amm.java.service.impl;

import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.AuthService;

import java.sql.SQLException;

public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;

    public AuthServiceImpl() {
        userRepository = new UserRepository();
    }

    @Override
    public boolean login(String login, String password) {
        try {
            UserEntity user = userRepository.findByLogin(login).orElseThrow(
                    () -> new RuntimeException("Такого пользователя не существует")
            ); // TO DO custom exception
            return BCrypt.checkpw(password, user.getPassword());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean register(String login, String password) {
        try {
            if (userRepository.findByLogin(login).isEmpty()) {
                UserEntity newUser = UserEntity.builder()
                        .login(login)
                        .password(BCrypt.hashpw(password, BCrypt.gensalt()))
                        .build();
                userRepository.save(newUser);
                return true;
            }
            return false;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
