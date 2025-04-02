package ru.vsu.amm.java.service.interfaces;

import ru.vsu.amm.java.model.requests.LoginRequest;
import ru.vsu.amm.java.model.requests.RegisterRequest;

public interface AuthService {

    void login(LoginRequest request);

    void register(RegisterRequest request);
}
