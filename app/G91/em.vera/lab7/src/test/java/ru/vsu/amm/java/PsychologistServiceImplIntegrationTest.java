package ru.vsu.amm.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.vsu.amm.java.config.DatabaseConfig;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.models.requests.PsychologistLoginRequest;
import ru.vsu.amm.java.repository.impl.PsychologistRepository;
import ru.vsu.amm.java.services.impl.PsychologistServiceImpl;
import ru.vsu.amm.java.utils.TestDataConstants;
import ru.vsu.amm.java.utils.TestDataFactory;

import javax.sql.DataSource;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PsychologistServiceImplIntegrationTest {

    private PsychologistServiceImpl service;
    private PsychologistRepository repo;
    private DataSource ds;

    @BeforeAll
    void init() {
        ds = DatabaseConfig.getDataSource();
        service = new PsychologistServiceImpl();
        repo = new PsychologistRepository();
    }

    @AfterEach
    void cleanup() throws Exception {
        try (var c = ds.getConnection();
             var st = c.createStatement()) {
            st.executeUpdate("DELETE FROM session");
            st.executeUpdate("DELETE FROM psychologist");
        }
    }

    @Test
    void registerSuccess() throws SQLException {
        var e = TestDataFactory.psychologistEntity();
        var req = TestDataFactory.validPsychologistRegisterRequest();
        service.register(req);
        assertTrue(repo.findByLogin(e.getLogin()).isPresent());
    }

    @Test
    void registerExistingThrows() throws SQLException {
        repo.save(TestDataFactory.psychologistEntity());
        var e = TestDataFactory.psychologistEntity();
        var req = TestDataFactory.validPsychologistRegisterRequest();
        assertThrows(WrongUserCredentialsException.class,
                () -> service.register(req)
        );
    }

    @Test
    void loginSuccess() {
        var reg = TestDataFactory.validPsychologistRegisterRequest();
        service.register(reg);
        assertDoesNotThrow(() ->
                service.login(new PsychologistLoginRequest(TestDataConstants.PSYCHOLOGIST_LOGIN, TestDataConstants.PSYCHOLOGIST_PASSWORD))
        );
    }

    @Test
    void loginWrongPasswordThrows() {
        var reg = TestDataFactory.validPsychologistRegisterRequest();
        service.register(reg);
        assertThrows(WrongUserCredentialsException.class,
                () -> service.login(new PsychologistLoginRequest(TestDataConstants.PSYCHOLOGIST_LOGIN, "bad"))
        );
    }
}
