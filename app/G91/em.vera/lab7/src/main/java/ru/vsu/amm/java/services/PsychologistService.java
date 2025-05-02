package ru.vsu.amm.java.services;

import ru.vsu.amm.java.models.dto.PsychologistDto;
import ru.vsu.amm.java.models.requests.PsychologistLoginRequest;
import ru.vsu.amm.java.models.requests.PsychologistRegisterRequest;

import java.util.List;
import java.util.Optional;

public interface PsychologistService {
    void login(PsychologistLoginRequest request);

    void register(PsychologistRegisterRequest request);

    List<PsychologistDto> getAllPsychologist();

    Optional<PsychologistDto> getPsychologistByLogin(String login);

    Optional<PsychologistDto> getPsychologistById(Long id);
}
