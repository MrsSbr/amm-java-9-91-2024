package ru.vsu.amm.java.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.exception.AuthException;
import ru.vsu.amm.java.exception.DatabaseException;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.AuthenticationService;
import ru.vsu.amm.java.util.PasswordHasher;

import java.sql.SQLException;

public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    private final UserRepository userRepository;

    public AuthenticationServiceImpl() {
        userRepository = new UserRepository();
    }

    @Override
    public UserEntity login(String email, String password) {
        try {
            UserEntity user = userRepository.findByEmail(email).orElseThrow(
                    () -> {
                        logger.info("Логин неудачен — пользователь не найден: {}", email);
                        return new AuthException("Неверный логин или пароль");
                    }
            );
            if (!PasswordHasher.matches(password, user.getHashPassword())) {
                logger.info("Неверный пароль для email: {}", email);
                throw new AuthException("Неверный логин или пароль");
            }

            logger.info("Успешный вход для пользователя: {}", email);
            return user;
        } catch (SQLException e) {
            logger.error("Ошибка базы данных при входе для email: {}", email, e);
            throw new DatabaseException("Ошибка с базой данных");
        }
    }

    @Override
    public UserEntity register(String email, String password) {
        try {
            if (userRepository.findByEmail(email).isEmpty()) {
                UserEntity user = new UserEntity(null, email, PasswordHasher.encode(password));
                userRepository.save(user);
                logger.info("Пользователь зарегистрирован: {}", email);
            } else {
                logger.info("Регистрация неудачна — email уже занят: {}", email);
                throw new AuthException("Логин занят");
            }

            return userRepository.findByEmail(email).get();
        } catch (SQLException e) {
            logger.error("Ошибка базы данных при регистрации email: {}", email, e);
            throw new DatabaseException("Ошибка с базой данных");
        }
    }
}
