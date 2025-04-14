import org.junit.jupiter.api.*;

import ru.vsu.amm.java.Entities.UserEntity;
import ru.vsu.amm.java.Enums.Roles;
import ru.vsu.amm.java.Repository.UserRepository;
import ru.vsu.amm.java.Configuration.DatabaseConfiguration;

import javax.sql.DataSource;
import java.sql.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class UserRepositoryITest {

    private UserRepository userRepository;
    private UserEntity testUser;

    @BeforeEach
    void setup() throws SQLException {
        userRepository = new UserRepository(DatabaseConfiguration.getDataSource());

        testUser = new UserEntity("user1", "password1");
        testUser.setUserRole(Roles.GUEST);
        testUser.setPhone("12345678901");
        testUser.setBirthDate(LocalDate.of(1990, 1, 1));

        userRepository.save(testUser);

        if (testUser.getUserID() == null) {
            throw new IllegalStateException("User ID error");
        }
    }

    @AfterEach
    void cleanup() throws SQLException {
        userRepository.findAll().forEach(user -> {
            try {
                userRepository.delete(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    void testSaveAndFindById() throws SQLException {
        assertNotNull(testUser.getUserID());

        Optional<UserEntity> foundUser = userRepository.findById(testUser.getUserID());

        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getUserName(), foundUser.get().getUserName());
        assertEquals(testUser.getUserPassword(), foundUser.get().getUserPassword());
        assertEquals(testUser.getUserRole(), foundUser.get().getUserRole());    }

    @Test
    void testFindByUserName() throws SQLException {
        Optional<UserEntity> foundUser = userRepository.findByUserName("user1");

        assertTrue(foundUser.isPresent());
        assertEquals("password1", foundUser.get().getUserPassword());
    }

    @Test
    void testUpdate() throws SQLException {
        testUser.setUserPassword("password11");
        userRepository.update(testUser);

        Optional<UserEntity> updatedUser = userRepository.findById(testUser.getUserID());

        assertTrue(updatedUser.isPresent());
        assertEquals("password11", updatedUser.get().getUserPassword());
    }

    @Test
    void testDelete() throws SQLException {
        userRepository.delete(testUser);

        Optional<UserEntity> deletedUser = userRepository.findById(testUser.getUserID());

        assertFalse(deletedUser.isPresent());
    }

}
