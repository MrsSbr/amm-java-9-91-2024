package ru.vsu.amm.java.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.exceptions.AuthException;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.AuthService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.vsu.amm.java.utils.UserFactory.makeRegisterRequest;
import static ru.vsu.amm.java.utils.UserFactory.makeSignInRequest;
import static ru.vsu.amm.java.utils.UserFactory.makeUser;

public class AuthServiceIntegrationTest {

    private AuthService authService;
    private UserRepository userRepository;

    @BeforeEach
    public void init() {

        userRepository = new UserRepository(DatabaseConfiguration.getTestDataSource());
        authService = new AuthService(userRepository);

    }

    @AfterEach
    public void tearDown() throws SQLException {

        DataSource dataSource = DatabaseConfiguration.getTestDataSource();

        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {

            stmt.executeUpdate("""
                    DELETE FROM "User"
                    """);

        }
    }

    @Test
    public void testRegistrationOfExistingUser() {

        User user = makeUser();

        userRepository.save(user);

        assertThrows(AuthException.class, () -> authService.register(makeRegisterRequest()));

    }

    @Test
    public void testRegistrationOfNewUser() {

        assertEquals(makeUser(), authService.register(makeRegisterRequest()));

    }

    @Test
    public void testSignInOfExistingUser() {

        User user = makeUser();

        user.setUserId(userRepository.save(user));

        assertEquals(user, authService.signIn(makeSignInRequest()));

    }

    @Test
    public void testSignInOfNewUser() {

        assertThrows(AuthException.class, () -> authService.signIn(makeSignInRequest()));

    }

}
