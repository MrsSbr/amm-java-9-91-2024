package ru.vsu.amm.java.service.interfaces;

import ru.vsu.amm.java.model.dto.UserDto;
import ru.vsu.amm.java.model.requests.LoginRequest;
import ru.vsu.amm.java.model.requests.RegisterRequest;

public interface AuthService {

    UserDto login(LoginRequest request);

    void register(RegisterRequest request);
}
