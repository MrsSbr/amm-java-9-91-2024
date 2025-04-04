package ru.vsu.amm.java.Service.Impl;

import lombok.AllArgsConstructor;
import ru.vsu.amm.java.Exception.AuthorizationException;
import ru.vsu.amm.java.Exception.DbException;
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

@AllArgsConstructor
public class DefaultAuthServiceImpl implements AuthService {

    private final UserRepo userRepository;
    private final BcryptPasswordEncoder bcryptPasswordEncoder;

    private static final int STATUS_BAD_REQUEST = 400;
    private static final int STATUS_SUCCESS = 200;

    private static final String USER_NOT_FOUND = "Пользователь не найден";
    private static final String INVALID_PASSWORD = "Неверный пароль";
    private static final String LOGIN_SUCCESS = "Успешный вход";
    private static final String DB_ERROR = "Ошибка бд";
    private static final String USER_ALREADY_EXISTS = "Пользователь с таким email уже существует";
    private static final String REGISTER_SUCCESS = "Успешная регистрация";

    @Override
    public LoginResponse login(LoginRequest request) {
        try {
            Optional<UserEntity> userOptional = userRepository.findByEmail(request.email());

            if (userOptional.isEmpty()) {
                return new LoginResponse(STATUS_BAD_REQUEST, USER_NOT_FOUND);
            }

            if (!bcryptPasswordEncoder.checkPassword(userOptional.get().getPassword(), request.password())) {
                return new LoginResponse(STATUS_BAD_REQUEST, INVALID_PASSWORD);
            }

        } catch (SQLException e) {
            throw new DbException(DB_ERROR);
        }

        return new LoginResponse(STATUS_SUCCESS, LOGIN_SUCCESS);
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        try {
            Optional<UserEntity> userOptional = userRepository.findByEmail(request.email());

            if (userOptional.isPresent()) {
                return new RegisterResponse(STATUS_BAD_REQUEST, USER_ALREADY_EXISTS);
            } else {
                UserEntity userEntity = new UserEntity(null, request.name(), request.email(), request.password(), request.phone());
                userRepository.save(userEntity);
            }

        } catch (SQLException e) {
            throw new DbException(DB_ERROR);
        }

        return new RegisterResponse(STATUS_SUCCESS, REGISTER_SUCCESS);
    }
}
