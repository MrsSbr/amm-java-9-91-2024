package ru.vsu.amm.java.services.clients;

import ru.vsu.amm.java.models.requests.ClientLoginRequest;
import ru.vsu.amm.java.models.requests.ClientRegisterRequest;

import java.util.Optional;

public interface ClientService {

    void login(ClientLoginRequest request);

    void register(ClientRegisterRequest request);

    Optional<Long> getClientIdByEmail(String email);
}
