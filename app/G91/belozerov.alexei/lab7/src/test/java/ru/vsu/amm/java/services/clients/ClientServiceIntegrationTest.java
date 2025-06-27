package ru.vsu.amm.java.services.clients;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.vsu.amm.java.IntegrationTest;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.models.requests.ClientLoginRequest;
import ru.vsu.amm.java.models.requests.ClientRegisterRequest;
import ru.vsu.amm.java.repositories.impl.ClientRepository;
import ru.vsu.amm.java.services.clients.impl.ClientServiceImpl;
import ru.vsu.amm.java.utils.SetupHelper;
import ru.vsu.amm.java.utils.TestApplicationConstants;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ClientServiceIntegrationTest extends IntegrationTest {

    private final ClientService service = new ClientServiceImpl();
    private final ClientRepository repository = new ClientRepository();

    @Test
    void registerSuccess() throws SQLException {
        ClientRegisterRequest req = SetupHelper.getClientRegisterRequest();
        service.register(req);

        Optional<?> saved = repository.findByEmail(TestApplicationConstants.CLIENT_EMAIL);
        assertTrue(saved.isPresent());
    }

    @Test
    void registerExistingThrows() throws SQLException {
        repository.save(SetupHelper.getClientEntity());
        ClientRegisterRequest req = SetupHelper.getClientRegisterRequest();

        assertThrows(WrongUserCredentialsException.class,
                () -> service.register(req)
        );
    }

    @Test
    void loginSuccess() {
        ClientRegisterRequest req = SetupHelper.getClientRegisterRequest();
        service.register(req);

        assertDoesNotThrow(() ->
                service.login(new ClientLoginRequest(TestApplicationConstants.CLIENT_EMAIL, TestApplicationConstants.CLIENT_PASSWORD))
        );
    }

    @Test
    void loginWrongPasswordThrows() {
        ClientRegisterRequest req = SetupHelper.getClientRegisterRequest();
        service.register(req);

        assertThrows(WrongUserCredentialsException.class,
                () -> service.login(new ClientLoginRequest(TestApplicationConstants.CLIENT_EMAIL, "bad"))
        );
    }
}
