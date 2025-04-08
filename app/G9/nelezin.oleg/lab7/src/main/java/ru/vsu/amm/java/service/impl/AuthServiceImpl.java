package ru.vsu.amm.java.service.impl;

import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.exception.DatabaseException;
import ru.vsu.amm.java.exception.WrongUserCredentialsException;
import ru.vsu.amm.java.filter.AuthFilter;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.AuthService;

import java.sql.SQLException;
import java.util.logging.Logger;

public class AuthServiceImpl implements AuthService {

    private static final Logger log = Logger.getLogger(AuthServiceImpl.class.getName());

    private final UserRepository userRepository;

    public AuthServiceImpl() {
        log.info("call AuthServiceImpl constructor");
        userRepository = new UserRepository();
    }

    @Override
    public boolean login(String login, String password) {
        log.info("try to login");
        try {
            UserEntity user = userRepository.findByLogin(login).orElseThrow(
                    () -> new WrongUserCredentialsException("Такого пользователя не существует")
            );
            log.info("user found");
            return BCrypt.checkpw(password, user.getPassword());
        } catch (SQLException e) {
            log.severe("error DatabaseException");
            throw new DatabaseException(e.getMessage());
        }
    }

    @Override
    public boolean register(String login, String password) {
        log.info("try to register");
        try {
            if (userRepository.findByLogin(login).isEmpty()) {
                UserEntity newUser = UserEntity.builder()
                        .login(login)
                        .password(BCrypt.hashpw(password, BCrypt.gensalt()))
                        .build();
                userRepository.save(newUser);
                log.info("user saved");
                return true;
            } else {
                log.severe("error WrongUserCredentialsException");
                throw new WrongUserCredentialsException("Пользователь с таким логином уже существует");
            }
        } catch (SQLException e) {
            log.severe("error DatabaseException");
            throw new DatabaseException(e.getMessage());
        }
    }
}
