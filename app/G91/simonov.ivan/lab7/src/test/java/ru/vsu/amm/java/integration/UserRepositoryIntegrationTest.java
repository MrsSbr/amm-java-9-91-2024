package ru.vsu.amm.java.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.enums.Role;
import ru.vsu.amm.java.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;

public class UserRepositoryIntegrationTest {

    private UserRepository userRepository;

    @BeforeEach
    public void init() {

        userRepository = new UserRepository(DatabaseConfiguration.getTestDataSource());

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

    private User makeUser() {

        return new User(
                "I",
                "I",
                "I",
                "i@i.ii",
                "1",
                Role.USER
        );

    }

    private int saveUser(User user) {

        int id = userRepository.save(user);
        Assertions.assertTrue(id > 0, "User saving failed!");

        return id;

    }

    @Test
    public void testSaveAndGetById() {

        User user = makeUser();

        int id = saveUser(user);
        user.setUserId(id);

        Optional<User> userById = userRepository.getById(id);
        Assertions.assertTrue(userById.isPresent(), "User wasn't found after saving!");

        Assertions.assertEquals(userById.get(), user, "User was found incorrectly!");

    }

    @Test
    public void testSaveAndUpdate() {

        User user = makeUser();

        int id = saveUser(user);
        user.setUserId(id);

        user.setLastName("U");

        Assertions.assertDoesNotThrow(() -> userRepository.update(user), "User update failed!");

        Assertions.assertEquals(
                user,
                userRepository.getById(id).orElse(null),
                "User after update didn't match updated version!");

    }

    @Test
    public void testSaveAndDelete() {

        User user = makeUser();

        int id = saveUser(user);

        Assertions.assertDoesNotThrow(() -> userRepository.delete(id), "User removal failed!");

        Assertions.assertEquals(
                Optional.empty(),
                userRepository.getById(id),
                "User was found after removal!");

    }

}
