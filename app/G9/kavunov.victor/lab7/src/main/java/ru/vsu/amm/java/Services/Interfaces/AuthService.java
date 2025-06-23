package ru.vsu.amm.java.Services.Interfaces;

import ru.vsu.amm.java.Entities.User;
import ru.vsu.amm.java.Requests.LoginRequest;
import ru.vsu.amm.java.Requests.RegisterRequest;

import java.sql.SQLException;
import ru.vsu.amm.java.Exceptions.AuthenticationException;

public interface AuthService {
    User login(LoginRequest request) throws SQLException, AuthenticationException;
    User register(RegisterRequest request) throws SQLException, AuthenticationException;
}
