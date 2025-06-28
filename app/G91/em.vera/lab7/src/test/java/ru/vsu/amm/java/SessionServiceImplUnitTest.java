package ru.vsu.amm.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import ru.vsu.amm.java.entities.Client;
import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.mappers.SessionMapper;
import ru.vsu.amm.java.models.dto.SessionDto;
import ru.vsu.amm.java.repository.impl.ClientRepository;
import ru.vsu.amm.java.repository.impl.PsychologistRepository;
import ru.vsu.amm.java.repository.impl.SessionRepository;
import ru.vsu.amm.java.services.impl.SessionServiceImpl;
import ru.vsu.amm.java.utils.TestDataConstants;
import ru.vsu.amm.java.utils.TestDataFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class SessionServiceImplUnitTest {

    private SessionServiceImpl service;
    private SessionRepository sessionRepo;
    private ClientRepository clientRepo;
    private PsychologistRepository psychRepo;

    @BeforeEach
    void setUp() {
        sessionRepo = mock(SessionRepository.class);
        clientRepo = mock(ClientRepository.class);
        psychRepo = mock(PsychologistRepository.class);

        service = new SessionServiceImpl(sessionRepo, clientRepo, psychRepo);
    }

    @Test
    void createSessionShouldCallSave() throws SQLException {
        SessionDto dto = SessionMapper.toSessionDto(TestDataFactory.sessionEntity());
        service.createSession(dto);

        ArgumentCaptor<Session> captor = ArgumentCaptor.forClass(Session.class);
        verify(sessionRepo).save(captor.capture());
        Session saved = captor.getValue();

        assertEquals(TestDataConstants.SESSION_ID,        saved.getIdSession());
        assertEquals(TestDataConstants.CLIENT_ID,         saved.getIdClient());
        assertEquals(TestDataConstants.PSYCHOLOGIST_ID,   saved.getIdPsychologist());
        assertEquals(TestDataConstants.SESSION_DATE,      saved.getDate());
        assertEquals(TestDataConstants.SESSION_PRICE,     saved.getPrice());
        assertEquals(TestDataConstants.SESSION_DURATION,  saved.getDuration());
    }


    @Test
    void cancelSessionShouldCallDelete() throws SQLException {
        service.cancelSession(42L);
        verify(sessionRepo).delete(42L);
    }

    @Test
    void getSessionByIdShouldReturnDtoWhenFound() throws SQLException {
        Session entity = TestDataFactory.sessionEntity();
        when(sessionRepo.findById(entity.getIdSession())).thenReturn(Optional.of(entity));

        Optional<SessionDto> got = service.getSessionById(entity.getIdSession());
        assertTrue(got.isPresent());
        assertEquals(TestDataConstants.SESSION_ID, got.get().getIdSession());

        verify(sessionRepo).findById(entity.getIdSession());
    }

    @Test
    void getSessionByIdShouldReturnEmptyWhenNotFound() throws SQLException {
        when(sessionRepo.findById(anyLong())).thenReturn(Optional.empty());
        assertTrue(service.getSessionById(99L).isEmpty());
    }

    @Test
    void getUpcomingByClientEmailShouldReturnList() throws SQLException {
        Client client = TestDataFactory.clientEntity();
        Session session = TestDataFactory.sessionEntity();

        when(clientRepo.findByEmail(TestDataConstants.CLIENT_EMAIL))
                .thenReturn(Optional.of(client));
        when(sessionRepo.findUpcomingSessionsByClientId(TestDataConstants.CLIENT_ID))
                .thenReturn(List.of(session));

        List<SessionDto> list = service.getUpcomingSessionsByClientEmail(TestDataConstants.CLIENT_EMAIL);
        assertEquals(1, list.size());
        assertEquals(TestDataConstants.SESSION_ID, list.get(0).getIdSession());

        verify(clientRepo).findByEmail(TestDataConstants.CLIENT_EMAIL);
        verify(sessionRepo).findUpcomingSessionsByClientId(TestDataConstants.CLIENT_ID);
    }

    @Test
    void getUpcomingByClientEmailShouldEmptyWhenNoClient() throws SQLException {
        when(clientRepo.findByEmail("nope@mail.com"))
                .thenReturn(Optional.empty());
        assertTrue(service.getUpcomingSessionsByClientEmail("nope@mail.com").isEmpty());
    }

    @Test
    void getAllSessionsShouldReturnMappedList() throws SQLException {
        Session entity = TestDataFactory.sessionEntity();
        when(sessionRepo.findAll()).thenReturn(List.of(entity));

        List<SessionDto> all = service.getAllSessions();
        assertEquals(1, all.size());
        assertEquals(TestDataConstants.SESSION_DURATION, all.get(0).getDuration());

        verify(sessionRepo).findAll();
    }

    @Test
    void updateSessionShouldCallUpdate() throws SQLException {
        SessionDto dto = SessionMapper.toSessionDto(TestDataFactory.sessionEntity());
        service.updateSession(dto);

        ArgumentCaptor<Session> captor = ArgumentCaptor.forClass(Session.class);
        verify(sessionRepo).update(captor.capture());
        Session updated = captor.getValue();

        assertEquals(TestDataConstants.SESSION_ID,        updated.getIdSession());
        assertEquals(TestDataConstants.CLIENT_ID,         updated.getIdClient());
        assertEquals(TestDataConstants.PSYCHOLOGIST_ID,   updated.getIdPsychologist());
        assertEquals(TestDataConstants.SESSION_DATE,      updated.getDate());
        assertEquals(TestDataConstants.SESSION_PRICE,     updated.getPrice());
        assertEquals(TestDataConstants.SESSION_DURATION,  updated.getDuration());
    }

    @Test
    void repositoryExceptionsShouldBeWrapped() throws SQLException {
        when(sessionRepo.findById(anyLong())).thenThrow(new SQLException("fail"));
        DataAccessException ex = assertThrows(
                DataAccessException.class,
                () -> service.getSessionById(1L)
        );
        assertEquals("Failed to fetch session by id", ex.getMessage());
    }
}
