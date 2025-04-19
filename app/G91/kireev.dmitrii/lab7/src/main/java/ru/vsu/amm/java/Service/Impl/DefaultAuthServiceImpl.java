package ru.vsu.amm.java.Service.Impl;

import ru.vsu.amm.java.Config.DatabaseConfig;
import ru.vsu.amm.java.Exception.DbException;
import ru.vsu.amm.java.Mapper.Impl.UserMapper;
import ru.vsu.amm.java.Model.Entity.UserEntity;
import ru.vsu.amm.java.Model.Request.LoginRequest;
import ru.vsu.amm.java.Model.Request.RegisterRequest;
import ru.vsu.amm.java.Model.Response.LoginResponse;
import ru.vsu.amm.java.Model.Response.RegisterResponse;
import ru.vsu.amm.java.Repository.Impl.UserRepo;
import ru.vsu.amm.java.Service.Interface.AuthService;
import ru.vsu.amm.java.Util.BcryptPasswordEncoder;

import java.sql.SQLException;
import java.util.Optional;
import java.util.logging.Logger;

public class DefaultAuthServiceImpl implements AuthService {

    private final UserRepo userRepo;
    private final BcryptPasswordEncoder bcryptPasswordEncoder;

    private static final int STATUS_BAD_REQUEST = 400;
    private static final int STATUS_SUCCESS = 200;

    private static final String USER_NOT_FOUND = "Пользователь не найден";
    private static final String INVALID_PASSWORD = "Неверный пароль";
    private static final String LOGIN_SUCCESS = "Успешный вход";
    private static final String DB_ERROR = "Ошибка бд";
    private static final String USER_ALREADY_EXISTS = "Пользователь с таким email уже существует";
    private static final String REGISTER_SUCCESS = "Успешная регистрация";

    private static final Logger log = Logger.getLogger(DefaultAuthServiceImpl.class.getName());

    public DefaultAuthServiceImpl() {
        this.userRepo = new UserRepo(DatabaseConfig.getDataSource(), new UserMapper());
        this.bcryptPasswordEncoder = new BcryptPasswordEncoder();
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        log.info("Попытка входа пользователя с email: " + request.email());

        try {
            Optional<UserEntity> userOptional = userRepo.findByEmail(request.email());

            if (userOptional.isEmpty()) {
                log.warning("Пользователь с email " + request.email() + " не найден.");
                return new LoginResponse(STATUS_BAD_REQUEST, USER_NOT_FOUND);
            }

            if (!bcryptPasswordEncoder.checkPassword(userOptional.get().getPassword(), request.password())) {
                log.warning("Неверный пароль для пользователя с email: " + request.email());
                return new LoginResponse(STATUS_BAD_REQUEST, INVALID_PASSWORD);
            }

        } catch (SQLException e) {
            log.severe("Ошибка при взаимодействии с базой данных при попытке входа: " + e.getMessage());
            throw new DbException(DB_ERROR);
        }

        log.info("Пользователь с email " + request.email() + " успешно вошел.");
        return new LoginResponse(STATUS_SUCCESS, LOGIN_SUCCESS);
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        log.info("Попытка регистрации пользователя с email: " + request.email());

        try {
            Optional<UserEntity> userOptional = userRepo.findByEmail(request.email());

            if (userOptional.isPresent()) {
                log.warning("Пользователь с email " + request.email() + " уже существует.");
                return new RegisterResponse(STATUS_BAD_REQUEST, USER_ALREADY_EXISTS);
            } else {
                UserEntity userEntity = new UserEntity(null, request.name(), request.email(), request.password(), request.phone());
                userRepo.save(userEntity);
                log.info("Пользователь с email " + request.email() + " успешно зарегистрирован.");
            }

        } catch (SQLException e) {
            log.severe("Ошибка при взаимодействии с базой данных при регистрации: " + e.getMessage());
            throw new DbException(DB_ERROR);
        }

        return new RegisterResponse(STATUS_SUCCESS, REGISTER_SUCCESS);
    }
}