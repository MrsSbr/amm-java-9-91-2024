package ru.vsu.amm.java.service.implementations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.enums.UserRole;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.model.requests.UserRequest;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.utils.BcryptPasswordEncoder;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

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
            stmt.executeUpdate("DELETE FROM \"user\"");
        }
    }

    @Test
    public void testRegisterNewUserSuccess() {
        UserRequest request = new UserRequest("Alice", "securePass");
        authManager.register(request);

        var user = userRepository.findByName("Alice");
        assertTrue(user.isPresent());
        assertEquals("Alice", user.get().getUserName());
    }

    @Test
    public void testRegisterExistingUserThrowsException() {
        UserEntity user = new UserEntity(null, "Bob", new BcryptPasswordEncoder(12).hashPassword("12345"), UserRole.USER);
        userRepository.save(user);

        UserRequest request = new UserRequest("Bob", "12345");

        assertThrows(WrongUserCredentialsException.class, () -> authManager.register(request));
    }

    @Test
    public void testLoginSuccessReturnsCorrectRole() {
        String name = "Carol";
        String password = "mySecret";
        String hashedPassword = new BcryptPasswordEncoder(12).hashPassword(password);

        UserEntity user = new UserEntity(null, name, hashedPassword, UserRole.ADMIN);
        userRepository.save(user);

        UserRequest request = new UserRequest(name, password);
        UserRole role = authManager.login(request);

        assertEquals(UserRole.ADMIN, role);
    }

    @Test
    public void testLoginWithWrongPasswordThrowsException() {
        String name = "Dave";
        String correctPassword = "correct";
        String wrongPassword = "wrong";

        UserEntity user = new UserEntity(null, name, new BcryptPasswordEncoder(12).hashPassword(correctPassword), UserRole.USER);
        userRepository.save(user);

        UserRequest request = new UserRequest(name, wrongPassword);

        assertThrows(WrongUserCredentialsException.class, () -> authManager.login(request));
    }

    @Test
    public void testLoginWithNonExistentUserThrowsException() {
        UserRequest request = new UserRequest("Ghost", "noSuchUser");
        assertThrows(WrongUserCredentialsException.class, () -> authManager.login(request));
    }
}
