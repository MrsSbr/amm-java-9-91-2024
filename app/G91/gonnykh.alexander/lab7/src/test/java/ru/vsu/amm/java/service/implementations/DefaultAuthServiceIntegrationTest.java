package ru.vsu.amm.java.service.implementations;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.enums.Role;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.model.dto.UserDto;
import ru.vsu.amm.java.model.requests.LoginRequest;
import ru.vsu.amm.java.model.requests.RegisterRequest;
import ru.vsu.amm.java.repository.implementations.UserRepository;
import ru.vsu.amm.java.utils.BcryptPasswordEncoder;

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
    public void setup() {
        DataSource dataSource = DatabaseConfiguration.getDataSource();
        this.userRepository = new UserRepository(dataSource);
        this.authService = new DefaultAuthService(userRepository, new BcryptPasswordEncoder());
    }

    @AfterEach
    public void cleanup() throws SQLException {
        DataSource dataSource = DatabaseConfiguration.getDataSource();
        try (Connection connection = dataSource.getConnection();
             Statement stmt = connection.createStatement()) {
            stmt.executeUpdate("DELETE FROM \"user\"");
        }
    }

    @Test
    public void testRegisterNewUserSuccess() {
        RegisterRequest request = new RegisterRequest("Alice", "alice@mail.com", "password123", Role.USER);
        authService.register(request);

        var user = userRepository.findByEmail("alice@mail.com");
        assertTrue(user.isPresent());
        assertEquals("Alice", user.get().getUsername());
    }

    @Test
    public void testRegisterExistingUserThrowsException() {
        RegisterRequest request = new RegisterRequest("Bob", "bob@mail.com", "pass123", Role.USER);
        UserEntity userEntity = new UserEntity(null, "Bob",
                new BcryptPasswordEncoder().hashPassword("pass123"), "bob@mail.com", Role.USER);
        userRepository.save(userEntity);

        assertThrows(WrongUserCredentialsException.class, () -> authService.register(request));
    }

    @Test
    public void testLoginWithCorrectCredentialsReturnsUserDto() {
        String email = "carol@mail.com";
        String rawPassword = "carol123";
        String hashedPassword = new BcryptPasswordEncoder().hashPassword(rawPassword);

        UserEntity userEntity = new UserEntity(null, "Carol", hashedPassword, email, Role.ADMIN);
        userRepository.save(userEntity);

        LoginRequest loginRequest = new LoginRequest(email, rawPassword);
        UserDto dto = authService.login(loginRequest);

        assertEquals("Carol", dto.getUsername());
        assertEquals(email, dto.getEmail());
        assertEquals(Role.ADMIN, dto.getRole());
    }

    @Test
    public void testLoginWithWrongPasswordThrowsException() {
        String email = "dave@mail.com";
        String password = "rightpass";
        UserEntity userEntity = new UserEntity(null, "Dave", new BcryptPasswordEncoder().hashPassword(password), email, Role.USER);
        userRepository.save(userEntity);

        LoginRequest loginRequest = new LoginRequest(email, "wrongpass");

        assertThrows(WrongUserCredentialsException.class, () -> authService.login(loginRequest));
    }

}