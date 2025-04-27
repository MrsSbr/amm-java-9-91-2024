package ru.vsu.amm.java.services;

import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.models.dto.SessionDto;

import java.util.List;
import java.util.Optional;

public interface SessionService {
    void createSession(SessionDto sessionDto);

    void cancelSession(Long sessionId);

    Optional<SessionDto> getSessionById(Long id);

    List<SessionDto> getSessionsByClientEmail(String email);

    List<SessionDto> getSessionsByPsychologistLogin(String login);

    List<SessionDto> getAllSessions();
}
