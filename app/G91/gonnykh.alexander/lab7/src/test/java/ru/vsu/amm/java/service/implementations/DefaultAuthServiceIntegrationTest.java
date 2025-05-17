package ru.vsu.amm.java.service.implementations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.enums.Role;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.model.dto.UserDto;
import ru.vsu.amm.java.model.requests.LoginRequest;
import ru.vsu.amm.java.model.requests.RegisterRequest;
import ru.vsu.amm.java.repository.implementations.UserRepository;
import ru.vsu.amm.java.utils.BcryptPasswordEncoder;
import utils.TestDataFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DefaultAuthServiceIntegrationTest {
    private DefaultAuthService authService;
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        DataSource ds = DatabaseConfiguration.getTestDataSource();
        userRepository = new UserRepository(ds);
        authService = new DefaultAuthService(userRepository, new BcryptPasswordEncoder());
    }

    @AfterEach
    void cleanup() throws SQLException {
        DataSource ds = DatabaseConfiguration.getTestDataSource();
        try (Connection conn = ds.getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate("DELETE FROM \"user\"");
        }
    }

    @Test
    void testRegisterNewUserSuccess() {
        RegisterRequest request = TestDataFactory.userRegister();
        authService.register(request);

        var user = userRepository.findByEmail("user@mail.com");
        assertTrue(user.isPresent());
        assertEquals("User", user.get().getUsername());
    }

    @Test
    void testRegisterExistingUserThrowsException() {
        RegisterRequest request = TestDataFactory.userRegister();
        userRepository.save(TestDataFactory.userEntity());

        assertThrows(WrongUserCredentialsException.class,
                () -> authService.register(request));
    }

    @Test
    void testLoginWithCorrectCredentialsReturnsUserDto() {
        userRepository.save(TestDataFactory.adminEntity());
        LoginRequest login = TestDataFactory.correctLoginRequest();
        UserDto dto = authService.login(login);

        assertEquals("Admin", dto.getUsername());
        assertEquals("admin@mail.com", dto.getEmail());
        assertEquals(Role.ADMIN, dto.getRole());
    }

    @Test
    void testLoginWithWrongPasswordThrowsException() {
        userRepository.save(TestDataFactory.adminEntity());
        LoginRequest login = TestDataFactory.wrongLoginRequest();

        assertThrows(WrongUserCredentialsException.class,
                () -> authService.login(login));
    }
}
