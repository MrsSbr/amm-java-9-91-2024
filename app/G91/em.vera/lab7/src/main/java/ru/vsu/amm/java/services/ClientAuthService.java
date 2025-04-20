package ru.vsu.amm.java.services;

import ru.vsu.amm.java.models.requests.ClientLoginRequest;
import ru.vsu.amm.java.models.requests.ClientRegisterRequest;

public interface ClientAuthService {
    void login(ClientLoginRequest request);
    void register(ClientRegisterRequest request);
}
