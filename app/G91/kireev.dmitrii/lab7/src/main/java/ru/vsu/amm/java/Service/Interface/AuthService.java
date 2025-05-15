package ru.vsu.amm.java.Service.Interface;

import ru.vsu.amm.java.Model.Request.LoginRequest;
import ru.vsu.amm.java.Model.Request.RegisterRequest;
import ru.vsu.amm.java.Model.Response.LoginResponse;
import ru.vsu.amm.java.Model.Response.RegisterResponse;

public interface AuthService {

    LoginResponse login(LoginRequest request);

    RegisterResponse register(RegisterRequest request);
}
