import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.Configuration.DbConfiguration;
import ru.vsu.amm.java.Entities.User;
import ru.vsu.amm.java.Repositories.UserRepository;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRepositoryTest {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() throws SQLException {
        userRepository = new UserRepository(DbConfiguration.getTestDataSource());
        clearUsersTable();
    }

    @AfterEach
    void tearDown() throws SQLException {
        clearUsersTable();
    }

    private void clearUsersTable() throws SQLException {
        var users = userRepository.findAll();
        for (User user : users) {
            userRepository.delete(user.getUserId());
        }
    }

    @Test
    void testSaveAndFindUserByEmail() throws SQLException {
        User user = new User();
        user.setSurname("Ivanov");
        user.setName("Ivan");
        user.setPatronymicname("Ivanovich");
        user.setPhoneNumber("1234567890");
        user.setEmail("ivanov@test.com");
        user.setPassword("password123");

        userRepository.save(user);

        Optional<User> found = userRepository.findByEmail("ivanov@test.com");
        assertTrue(found.isPresent());
        assertEquals("ivanov@test.com", found.get().getEmail());
        assertEquals("1234567890", found.get().getPhoneNumber());
    }

    @Test
    void testFindByPhoneNumber() throws SQLException {
        User user = new User();
        user.setSurname("Petrov");
        user.setName("Petr");
        user.setPatronymicname("Petrovich");
        user.setPhoneNumber("0987654321");
        user.setEmail("petrov@test.com");
        user.setPassword("password456");

        userRepository.save(user);

        Optional<User> found = userRepository.findByPhoneNumber("0987654321");
        assertTrue(found.isPresent());
        assertEquals("petrov@test.com", found.get().getEmail());
    }

    @Test
    void testUpdateUser() throws SQLException {
        User user = new User();
        user.setSurname("Sidorov");
        user.setName("Sidr");
        user.setPatronymicname("Sidorovich");
        user.setPhoneNumber("111222333");
        user.setEmail("sidorov@test.com");
        user.setPassword("password789");

        userRepository.save(user);

        Optional<User> savedUser = userRepository.findByEmail("sidorov@test.com");
        assertTrue(savedUser.isPresent());

        User toUpdate = savedUser.get();
        toUpdate.setSurname("UpdatedSurname");
        toUpdate.setPhoneNumber("999888777");
        toUpdate.setEmail("updated@test.com");

        userRepository.update(toUpdate);

        assertTrue(userRepository.findByEmail("updated@test.com").isPresent());
        assertFalse(userRepository.findByEmail("sidorov@test.com").isPresent());
    }

    @Test
    void testDeleteUser() throws SQLException {
        User user = new User();
        user.setSurname("Smirnov");
        user.setName("Sergey");
        user.setPatronymicname("Sergeevich");
        user.setPhoneNumber("444555666");
        user.setEmail("smirnov@test.com");
        user.setPassword("password000");

        userRepository.save(user);

        Optional<User> savedUser = userRepository.findByEmail("smirnov@test.com");
        assertTrue(savedUser.isPresent());

        Long userId = savedUser.get().getUserId();
        userRepository.delete(userId);

        assertFalse(userRepository.findByEmail("smirnov@test.com").isPresent());
    }

    @Test
    void testFindAllUsers() throws SQLException {
        User user1 = new User();
        user1.setSurname("Kuznetsov");
        user1.setName("Alex");
        user1.setPatronymicname("Alexeevich");
        user1.setPhoneNumber("555666777");
        user1.setEmail("kuznetsov@test.com");
        user1.setPassword("pass1");
        userRepository.save(user1);

        User user2 = new User();
        user2.setSurname("Volkov");
        user2.setName("Vladimir");
        user2.setPatronymicname("Vladimirovich");
        user2.setPhoneNumber("666777888");
        user2.setEmail("volkov@test.com");
        user2.setPassword("pass2");
        userRepository.save(user2);

        var allUsers = userRepository.findAll();
        assertNotNull(allUsers);
        assertTrue(allUsers.size() >= 2);
    }
}
