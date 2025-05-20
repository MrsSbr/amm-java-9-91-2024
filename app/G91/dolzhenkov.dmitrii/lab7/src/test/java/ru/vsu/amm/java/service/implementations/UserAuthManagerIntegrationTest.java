package ru.vsu.amm.java.service.implementations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.model.requests.UserRequest;
import ru.vsu.amm.java.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.vsu.amm.java.service.implementations.UserTestData.USERNAME_ALICE;
import static ru.vsu.amm.java.service.implementations.UserTestData.aliceRequest;
import static ru.vsu.amm.java.service.implementations.UserTestData.bobEntity;
import static ru.vsu.amm.java.service.implementations.UserTestData.bobRequest;
import static ru.vsu.amm.java.service.implementations.UserTestData.carolEntity;
import static ru.vsu.amm.java.service.implementations.UserTestData.carolRequest;
import static ru.vsu.amm.java.service.implementations.UserTestData.daveEntity;
import static ru.vsu.amm.java.service.implementations.UserTestData.daveWrongRequest;
import static ru.vsu.amm.java.service.implementations.UserTestData.ghostRequest;

class UserAuthManagerIntegrationTest {

    private UserAuthManager authManager;
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        DataSource dataSource = DatabaseConfiguration.getTestDataSource();
        this.userRepository = new UserRepository(dataSource);
        this.authManager = new UserAuthManager();
    }

    @AfterEach
    public void cleanup() throws SQLException {
        DataSource dataSource = DatabaseConfiguration.getTestDataSource();
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM User");
        }
    }

    @Test
    public void testRegisterNewUserSuccess() {
        UserRequest request = aliceRequest();
        authManager.register(request);

        var user = userRepository.findByName(USERNAME_ALICE);
        assertTrue(user.isPresent());
        assertEquals(USERNAME_ALICE, user.get().getUserName());
    }

    @Test
    public void testRegisterExistingUserThrowsException() {
        userRepository.save(bobEntity());
        UserRequest request = bobRequest();

        assertThrows(WrongUserCredentialsException.class, () -> authManager.register(request));
    }

    @Test
    public void testLoginSuccessReturnsCorrectRole() {
        userRepository.save(carolEntity());
        UserRequest request = carolRequest();

        var role = authManager.login(request);

        assertEquals(carolEntity().getUserRole(), role);
    }

    @Test
    public void testLoginWithWrongPasswordThrowsException() {
        userRepository.save(daveEntity());
        UserRequest request = daveWrongRequest();

        assertThrows(WrongUserCredentialsException.class, () -> authManager.login(request));
    }

    @Test
    public void testLoginWithNonExistentUserThrowsException() {
        UserRequest request = ghostRequest();

        assertThrows(WrongUserCredentialsException.class, () -> authManager.login(request));
    }
}