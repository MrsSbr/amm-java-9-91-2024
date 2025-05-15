package ru.vsu.amm.java.service;

import ru.vsu.amm.java.repository.SessionRepository;

public class DeleteService {

    private final SessionRepository sessionRepository;

    public DeleteService() {

        sessionRepository = new SessionRepository();

    }

    public void deleteSession(int sessionId) {

        sessionRepository.delete(sessionId);

    }
}
