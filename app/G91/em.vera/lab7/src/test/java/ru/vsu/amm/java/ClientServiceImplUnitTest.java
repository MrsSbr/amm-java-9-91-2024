package ru.vsu.amm.java;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;
import ru.vsu.amm.java.entities.Client;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.models.requests.ClientLoginRequest;
import ru.vsu.amm.java.models.requests.ClientRegisterRequest;
import ru.vsu.amm.java.repository.impl.ClientRepository;
import ru.vsu.amm.java.services.impl.ClientServiceImpl;
import ru.vsu.amm.java.utils.TestDataConstants;
import ru.vsu.amm.java.utils.TestDataFactory;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ClientServiceImplUnitTest {

    private ClientServiceImpl service;
    private ClientRepository repo;

    @BeforeEach
    void setUp() {
        repo = mock(ClientRepository.class);
        service = new ClientServiceImpl(repo);
    }

    @Test
    void loginShouldPassWhenCorrectCredentials() throws SQLException {
        Client c = TestDataFactory.clientEntity();
        when(repo.findByEmail(c.getEmail())).thenReturn(Optional.of(c));

        try (MockedStatic<BCrypt> bc = mockStatic(BCrypt.class)) {
            bc.when(() -> BCrypt.checkpw("plain", c.getPassword())).thenReturn(true);

            assertDoesNotThrow(() ->
                    service.login(new ClientLoginRequest(c.getEmail(), "plain"))
            );
        }

        verify(repo).findByEmail(c.getEmail());
    }

    @Test
    void loginShouldThrowWhenClientNotFound() throws SQLException {
        when(repo.findByEmail("no@mail.com")).thenReturn(Optional.empty());

        WrongUserCredentialsException ex = assertThrows(
                WrongUserCredentialsException.class,
                () -> service.login(new ClientLoginRequest("no@mail.com", "any"))
        );
        assertEquals("Client with email:no@mail.com not found", ex.getMessage());
        verify(repo).findByEmail("no@mail.com");
    }

    @Test
    void loginShouldThrowWhenPasswordIncorrect() throws SQLException {
        Client c = TestDataFactory.clientEntity();
        when(repo.findByEmail(c.getEmail())).thenReturn(Optional.of(c));

        try (MockedStatic<BCrypt> bc = mockStatic(BCrypt.class)) {
            bc.when(() -> BCrypt.checkpw("bad", c.getPassword())).thenReturn(false);

            WrongUserCredentialsException ex = assertThrows(
                    WrongUserCredentialsException.class,
                    () -> service.login(new ClientLoginRequest(c.getEmail(), "bad"))
            );
            assertEquals("Incorrect password", ex.getMessage());
        }

        verify(repo).findByEmail(c.getEmail());
    }

    @Test
    void registerShouldThrowWhenClientExists() throws SQLException {
        ClientRegisterRequest req = TestDataFactory.validClientRegisterRequest();
        when(repo.findByEmail(req.email())).thenReturn(Optional.of(TestDataFactory.clientEntity()));

        WrongUserCredentialsException ex = assertThrows(
                WrongUserCredentialsException.class,
                () -> service.register(req)
        );
        assertEquals(
                String.format("Client with email %s already exists", TestDataConstants.CLIENT_EMAIL),
                ex.getMessage()
        );

        verify(repo).findByEmail(req.email());
    }

    @Test
    void registerShouldSaveWhenNewClient() throws SQLException {
        ClientRegisterRequest req = TestDataFactory.validClientRegisterRequest();
        when(repo.findByEmail(req.email())).thenReturn(Optional.empty());

        try (MockedStatic<BCrypt> bc = mockStatic(BCrypt.class)) {
            bc.when(() -> BCrypt.gensalt()).thenReturn("salt");
            bc.when(() -> BCrypt.hashpw(TestDataConstants.CLIENT_PASSWORD, "salt")).thenReturn("h");

            service.register(req);

            ArgumentCaptor<Client> captor = ArgumentCaptor.forClass(Client.class);
            verify(repo).save(captor.capture());

            Client saved = captor.getValue();
            assertEquals(TestDataConstants.CLIENT_EMAIL, saved.getEmail());
            assertEquals(TestDataConstants.CLIENT_NAME, saved.getName());
            assertEquals("h", saved.getPassword());
        }
    }
}
