package ru.vsu.amm.java.services;

import ru.vsu.amm.java.models.requests.PsychologistLoginRequest;
import ru.vsu.amm.java.models.requests.PsychologistRegisterRequest;

public interface PsychologistAuthService {
    void login(PsychologistLoginRequest request);
    void register(PsychologistRegisterRequest request);
}
