package ru.vsu.amm.java.service.interfaces;

import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.requests.LoginRequest;
import ru.vsu.amm.java.requests.RegisterRequest;

import javax.naming.AuthenticationException;
import java.sql.SQLException;

public interface AuthService {
    UserEntity login(LoginRequest request) throws SQLException, AuthenticationException;
    UserEntity register(RegisterRequest request) throws SQLException, AuthenticationException;
}
