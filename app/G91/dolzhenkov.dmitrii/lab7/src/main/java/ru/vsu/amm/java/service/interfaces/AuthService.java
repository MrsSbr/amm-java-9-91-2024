package ru.vsu.amm.java.service.interfaces;

import ru.vsu.amm.java.model.requests.UserRequest;

public interface AuthService {

    void login(UserRequest request);

    void register(UserRequest request);
}
