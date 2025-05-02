package ru.vsu.amm.java.services;

import ru.vsu.amm.java.models.dto.SessionDto;

import java.util.List;
import java.util.Optional;

public interface SessionService {
    void createSession(SessionDto sessionDto);

    void cancelSession(Long sessionId);

    void updateSession(SessionDto sessionDto);

    Optional<SessionDto> getSessionById(Long id);

    List<SessionDto> getUpcomingSessionsByClientEmail(String email);

    List<SessionDto> getUpcomingSessionsByPsychologistLogin(String login);

    List<SessionDto> getAllSessions();
}
