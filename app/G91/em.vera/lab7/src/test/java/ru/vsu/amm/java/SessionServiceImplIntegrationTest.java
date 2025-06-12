package ru.vsu.amm.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.vsu.amm.java.config.DatabaseConfig;
import ru.vsu.amm.java.models.dto.SessionDto;
import ru.vsu.amm.java.services.ClientService;
import ru.vsu.amm.java.services.PsychologistService;
import ru.vsu.amm.java.services.SessionService;
import ru.vsu.amm.java.services.impl.ClientServiceImpl;
import ru.vsu.amm.java.services.impl.PsychologistServiceImpl;
import ru.vsu.amm.java.services.impl.SessionServiceImpl;
import ru.vsu.amm.java.utils.TestDataConstants;
import ru.vsu.amm.java.utils.TestDataFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SessionServiceImplIntegrationTest {

    private SessionService sessionService;
    private ClientService clientService;
    private PsychologistService psychService;
    private DataSource ds;

    @BeforeAll
    void init() {
        ds = DatabaseConfig.getDataSource();
        clientService = new ClientServiceImpl();
        psychService = new PsychologistServiceImpl();
        sessionService = new SessionServiceImpl();
    }

    @AfterEach
    void cleanup() throws Exception {
        try (Connection c = ds.getConnection();
             Statement st = c.createStatement()) {
            st.executeUpdate("DELETE FROM session");
            st.executeUpdate("DELETE FROM psychologist");
            st.executeUpdate("DELETE FROM client");
        }
    }

    @Test
    void fullFlowCreateAndCancelSession() {
        clientService.register(TestDataFactory.validClientRegisterRequest());
        var clientOpt = clientService.getClientByEmail(TestDataConstants.CLIENT_EMAIL);
        assertTrue(clientOpt.isPresent());
        Long actualClientId = clientOpt.get().getIdClient();

        psychService.register(TestDataFactory.validPsychologistRegisterRequest());
        var psychOpt = psychService.getPsychologistByLogin(TestDataConstants.PSYCHOLOGIST_LOGIN);
        assertTrue(psychOpt.isPresent());
        Long actualPsychId = psychOpt.get().getIdPsychologist();

        LocalDate futureDate = LocalDate.now().plusDays(1);

        SessionDto toCreate = SessionDto.builder()
                .idClient(actualClientId)
                .idPsychologist(actualPsychId)
                .date(futureDate)
                .price(TestDataConstants.SESSION_PRICE)
                .duration(TestDataConstants.SESSION_DURATION)
                .build();

        sessionService.createSession(toCreate);

        List<SessionDto> upcoming = sessionService.getUpcomingSessionsByClientEmail(TestDataConstants.CLIENT_EMAIL);
        assertEquals(1, upcoming.size());
        Long sessionId = upcoming.get(0).getIdSession();
        assertNotNull(sessionId);

        sessionService.cancelSession(sessionId);
        Optional<SessionDto> maybe = sessionService.getSessionById(sessionId);
        assertTrue(maybe.isEmpty());
    }
}
