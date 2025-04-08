package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.repository.SessionRepository;

public class DeleteService {

    private final SessionRepository sessionRepository;

    public DeleteService() {

        sessionRepository = new SessionRepository();

    }

    public void deleteSession(Session session) {

        sessionRepository.delete(session);

    }
}
