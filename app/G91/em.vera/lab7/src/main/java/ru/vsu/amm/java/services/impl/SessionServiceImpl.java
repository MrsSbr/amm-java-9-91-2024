package ru.vsu.amm.java.services.impl;

import lombok.extern.slf4j.Slf4j;
import ru.vsu.amm.java.entities.Client;
import ru.vsu.amm.java.entities.Psychologist;
import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.mappers.SessionMapper;
import ru.vsu.amm.java.models.dto.SessionDto;
import ru.vsu.amm.java.repository.impl.ClientRepository;
import ru.vsu.amm.java.repository.impl.PsychologistRepository;
import ru.vsu.amm.java.repository.impl.SessionRepository;
import ru.vsu.amm.java.services.SessionService;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
public class SessionServiceImpl implements SessionService {
    private final SessionRepository sessionRepository;
    private final ClientRepository clientRepository;
    private final PsychologistRepository psychologistRepository;

    public SessionServiceImpl() {
        this.sessionRepository = new SessionRepository();
        this.clientRepository = new ClientRepository();
        this.psychologistRepository = new PsychologistRepository();
    }

    @Override
    public void createSession(Session session) {
        try {
            sessionRepository.save(session);
        } catch (SQLException e) {
            log.error("Error creating session: {}", session, e);
            throw new DataAccessException("Failed to create session", e);
        }
    }

    @Override
    public void cancelSession(Long sessionId) {
        try {
            sessionRepository.delete(sessionId);
        } catch (SQLException e) {
            log.error("Error cancelling session id={}", sessionId, e);
            throw new DataAccessException("Failed to cancel session", e);
        }
    }

    @Override
    public Optional<SessionDto> getSessionById(Long id) {
        try {
            return sessionRepository.findById(id).map(SessionMapper::toSessionDto);
        } catch (SQLException e) {
            log.error("Error fetching session by id={}", id, e);
            throw new DataAccessException("Failed to fetch session by id", e);
        }
    }

    @Override
    public List<SessionDto> getSessionsByClientEmail(String email) {
        try {
            Optional<Client> clientOpt = clientRepository.findByEmail(email);
            if (clientOpt.isPresent()) {
                Long clientId = clientOpt.get().getIdClient();
                return sessionRepository.findByClient(clientId).stream().map(SessionMapper::toSessionDto).toList();
            } else {
                return Collections.emptyList();
            }
        } catch (SQLException e) {
            log.error("Error fetching sessions for client email={}", email, e);
            throw new DataAccessException("Failed to fetch sessions by client email", e);
        }
    }

    @Override
    public List<SessionDto> getSessionsByPsychologistLogin(String login) {
        try {
            Optional<Psychologist> psychologistOpt = psychologistRepository.findByLogin(login);
            if (psychologistOpt.isPresent()) {
                Long psychologistId = psychologistOpt.get().getIdPsychologist();
                return sessionRepository.findByPsychologist(psychologistId).stream().map(SessionMapper::toSessionDto).toList();
            } else {
                return Collections.emptyList();
            }
        } catch (SQLException e) {
            log.error("Error fetching sessions for psychologist login={}", login, e);
            throw new DataAccessException("Failed to fetch sessions by psychologist login", e);
        }
    }

    @Override
    public List<SessionDto> getAllSessions() {
        try {
            return sessionRepository.findAll().stream().map(SessionMapper::toSessionDto).toList();
        } catch (SQLException e) {
            log.error("Error fetching all sessions", e);
            throw new DataAccessException("Failed to fetch all sessions", e);
        }
    }

}
