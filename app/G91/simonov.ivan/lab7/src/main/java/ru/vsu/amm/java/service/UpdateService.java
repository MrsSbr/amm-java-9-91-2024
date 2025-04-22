package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.exceptions.UpdateException;
import ru.vsu.amm.java.repository.SessionRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class UpdateService {

    private final SessionRepository sessionRepository;

    public UpdateService() {

        sessionRepository = new SessionRepository();

    }

    public void update(int sessionId) {

        Optional<Session> repSession = sessionRepository.getById(sessionId);

        try {

            if (repSession.isPresent()) {

                repSession.get().setExitDate(LocalDateTime.now());
                sessionRepository.update(repSession.get());

            } else {

                throw new UpdateException("Session with id " + sessionId + " does not exist!");

            }

        } catch (IllegalArgumentException e) {

            throw new UpdateException(e.getMessage());

        }

    }
}
