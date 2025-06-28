package ru.vsu.amm.java;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.vsu.amm.java.config.DatabaseConfig;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.models.requests.ClientLoginRequest;
import ru.vsu.amm.java.models.requests.ClientRegisterRequest;
import ru.vsu.amm.java.repository.impl.ClientRepository;
import ru.vsu.amm.java.services.impl.ClientServiceImpl;
import ru.vsu.amm.java.utils.TestDataConstants;
import ru.vsu.amm.java.utils.TestDataFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ClientServiceImplIntegrationTest {

    private ClientServiceImpl service;
    private ClientRepository repo;
    private DataSource ds;

    @BeforeAll
    void init() {
        ds = DatabaseConfig.getDataSource();
        service = new ClientServiceImpl();
        repo = new ClientRepository();
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
    void registerSuccess() throws SQLException {
        ClientRegisterRequest req = TestDataFactory.validClientRegisterRequest();
        service.register(req);

        Optional<?> saved = repo.findByEmail(TestDataConstants.CLIENT_EMAIL);
        assertTrue(saved.isPresent());
    }

    @Test
    void registerExistingThrows() throws SQLException {
        repo.save(TestDataFactory.clientEntity());
        ClientRegisterRequest req = TestDataFactory.validClientRegisterRequest();

        assertThrows(WrongUserCredentialsException.class,
                () -> service.register(req)
        );
    }

    @Test
    void loginSuccess() {
        ClientRegisterRequest req = TestDataFactory.validClientRegisterRequest();
        service.register(req);

        assertDoesNotThrow(() ->
                service.login(new ClientLoginRequest(TestDataConstants.CLIENT_EMAIL, TestDataConstants.CLIENT_PASSWORD))
        );
    }

    @Test
    void loginWrongPasswordThrows() {
        ClientRegisterRequest req = TestDataFactory.validClientRegisterRequest();
        service.register(req);

        assertThrows(WrongUserCredentialsException.class,
                () -> service.login(new ClientLoginRequest(TestDataConstants.CLIENT_EMAIL, "bad"))
        );
    }
}
