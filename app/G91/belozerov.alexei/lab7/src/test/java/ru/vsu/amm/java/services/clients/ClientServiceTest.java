package ru.vsu.amm.java.services.clients;

import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import ru.vsu.amm.java.entities.ClientEntity;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ClientServiceTest {

    private final ClientRepository repository = mock(ClientRepository.class);
    private final ClientService service = new ClientServiceImpl(repository);

    @Test
    void loginShouldPassWhenCorrectCredentials() throws SQLException {
        ClientEntity c = SetupHelper.getClientEntity();
        when(repository.findByEmail(c.getEmail())).thenReturn(Optional.of(c));

        try (MockedStatic<BCrypt> bc = mockStatic(BCrypt.class)) {
            bc.when(() -> BCrypt.checkpw("password", c.getPassword())).thenReturn(true);

            assertDoesNotThrow(() ->
                    service.login(new ClientLoginRequest(c.getEmail(), "password"))
            );
        }

        verify(repository).findByEmail(c.getEmail());
    }

    @Test
    void loginShouldThrowWhenClientNotFound() throws SQLException {
        when(repository.findByEmail("no@mail.com")).thenReturn(Optional.empty());

        WrongUserCredentialsException ex = assertThrows(
                WrongUserCredentialsException.class,
                () -> service.login(new ClientLoginRequest("no@mail.com", "any"))
        );
        assertEquals("Client with email:no@mail.com not found", ex.getMessage());
        verify(repository).findByEmail("no@mail.com");
    }

    @Test
    void loginShouldThrowWhenPasswordIncorrect() throws SQLException {
        ClientEntity c = SetupHelper.getClientEntity();
        when(repository.findByEmail(c.getEmail())).thenReturn(Optional.of(c));

        try (MockedStatic<BCrypt> bc = mockStatic(BCrypt.class)) {
            bc.when(() -> BCrypt.checkpw("bad", c.getPassword())).thenReturn(false);

            WrongUserCredentialsException ex = assertThrows(
                    WrongUserCredentialsException.class,
                    () -> service.login(new ClientLoginRequest(c.getEmail(), "bad"))
            );
            assertEquals("Incorrect password", ex.getMessage());
        }

        verify(repository).findByEmail(c.getEmail());
    }

    @Test
    void registerShouldThrowWhenClientExists() throws SQLException {
        ClientRegisterRequest req = SetupHelper.getClientRegisterRequest();
        when(repository.findByEmail(req.email())).thenReturn(Optional.of(SetupHelper.getClientEntity()));

        WrongUserCredentialsException ex = assertThrows(
                WrongUserCredentialsException.class,
                () -> service.register(req)
        );
        assertEquals(
                String.format("Client with email %s already exists", TestApplicationConstants.CLIENT_EMAIL),
                ex.getMessage()
        );

        verify(repository).findByEmail(req.email());
    }

    @Test
    void registerShouldSaveWhenNewClient() throws SQLException {
        ClientRegisterRequest req = SetupHelper.getClientRegisterRequest();
        when(repository.findByEmail(req.email())).thenReturn(Optional.empty());

        try (MockedStatic<BCrypt> bc = mockStatic(BCrypt.class)) {
            bc.when(() -> BCrypt.gensalt()).thenReturn("salt");
            bc.when(() -> BCrypt.hashpw(TestApplicationConstants.CLIENT_PASSWORD, "salt")).thenReturn("h");

            service.register(req);

            ArgumentCaptor<ClientEntity> captor = ArgumentCaptor.forClass(ClientEntity.class);
            verify(repository).save(captor.capture());

            ClientEntity saved = captor.getValue();
            assertEquals(TestApplicationConstants.CLIENT_EMAIL, saved.getEmail());
            assertEquals(TestApplicationConstants.CLIENT_NAME, saved.getName());
            assertEquals("h", saved.getPassword());
        }
    }
}
