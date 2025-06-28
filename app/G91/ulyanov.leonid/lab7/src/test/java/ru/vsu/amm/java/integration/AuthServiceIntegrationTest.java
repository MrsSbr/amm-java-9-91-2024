package ru.vsu.amm.java.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.config.DbConfig;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repos.UserRepository;
import ru.vsu.amm.java.requests.Auth.LoginRequest;
import ru.vsu.amm.java.requests.Auth.RegisterRequest;
import ru.vsu.amm.java.services.AuthService;
import ru.vsu.amm.java.utils.UserFactory;

import javax.naming.AuthenticationException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class AuthServiceIntegrationTest {

    private AuthService authService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws SQLException {
        authService = new AuthService();
        userRepository = new UserRepository();
        clearUsersTable();
    }

    @AfterEach
    void tearDown() throws SQLException {
        clearUsersTable();
    }

    private void clearUsersTable() throws SQLException {
        DataSource ds = DbConfig.getDataSource();
        try (Connection connection = ds.getConnection();
             Statement statement = connection.createStatement()) {
            statement.executeUpdate("DELETE FROM \"User\"");
        }
    }

    @Test
    void testRegisterNewUserSuccess() throws AuthenticationException {
        RegisterRequest request = UserFactory.correctRegisterRequest();

        User registered = authService.register(request);

        assertNotNull(registered.getUserId());
        assertEquals(request.email(), registered.getEmail());

        Optional<User> fromDb = userRepository.getByEmail(request.email());
        assertTrue(fromDb.isPresent());
        assertEquals(registered.getUserId(), fromDb.get().getUserId());
    }

    @Test
    void testRegisterExistingUserThrowsException() {
        userRepository.create(UserFactory.newUser());
        RegisterRequest request = UserFactory.wrongRegisterRequest();

        assertThrows(AuthenticationException.class, () -> authService.register(request));
    }

    @Test
    void testLoginWithCorrectCredentialsReturnsUser() throws AuthenticationException {
        userRepository.create(UserFactory.newUser());
        LoginRequest loginRequest = UserFactory.correctLoginRequest();

        User logged = authService.login(loginRequest);

        assertEquals(loginRequest.email(), logged.getEmail());
        assertEquals("Firstname", logged.getFirstName());
    }

    @Test
    void testLoginWithWrongPasswordThrowsException() {
        userRepository.create(UserFactory.newUser());
        LoginRequest wrongLoginRequest = UserFactory.wrongLoginRequest();

        assertThrows(AuthenticationException.class, () -> authService.login(wrongLoginRequest));
    }
} 