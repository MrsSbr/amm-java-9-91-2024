package ru.vsu.amm.java.integration;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static ru.vsu.amm.java.utils.UserFactory.makeFewUsers;
import static ru.vsu.amm.java.utils.UserFactory.makeUser;

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

    private int saveUser(User user) {

        int id = userRepository.save(user);
        Assertions.assertTrue(id > 0, "User saving failed!");

        user.setUserId(id);

        return id;

    }

    @Test
    public void testSaveAndGetByLoginAndPassword() {

        User user = makeUser();

        saveUser(user);

        Optional<User> userById = userRepository.getByLoginAndPassword(user.getLogin(), user.getPassword());
        Assertions.assertTrue(userById.isPresent(), "User wasn't found after saving!");

        Assertions.assertEquals(userById.get(), user, "User was found incorrectly!");

    }

    @Test
    public void testSaveAndGetById() {

        User user = makeUser();

        int id = saveUser(user);

        Optional<User> userById = userRepository.getById(id);
        Assertions.assertTrue(userById.isPresent(), "User wasn't found after saving!");

        Assertions.assertEquals(userById.get(), user, "User was found incorrectly!");

    }

    @Test
    public void testSaveAndGetAll() {

        List<User> users = makeFewUsers();

        for (User user : users) {

            int id = saveUser(user);
            user.setUserId(id);

        }

        List<User> allUsers = userRepository.getAll();

        Assertions.assertEquals(
                allUsers.size(), users.size(),
                "Database contains a different number of users than the saved one!");

        Assertions.assertTrue(allUsers.containsAll(users), "Not all saved users were found!");

    }

    @Test
    public void testSaveAndUpdate() {

        User user = makeUser();

        int id = saveUser(user);

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
