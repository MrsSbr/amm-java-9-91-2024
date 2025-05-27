package repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.repository.UserRepository;

import java.sql.SQLException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRepositoryIT {
    private UserRepository userRepository;
    private UserEntity testUser;

    @BeforeEach
    void setUp() throws SQLException {
        userRepository = new UserRepository();

        testUser = new UserEntity("test_user", "email@email", "password");

        userRepository.save(testUser);

        testUser = userRepository.findByUsername("test_user")
                .orElseThrow();
    }

    @AfterEach
    void cleanup() throws SQLException {
        UserEntity userToDelete = new UserEntity();
        userToDelete.setUserId(testUser.getUserId());
        userRepository.delete(userToDelete);
    }

    @Test
    void findByUsername_ShouldReturnCorrectUser() throws SQLException {
        Optional<UserEntity> foundUser = userRepository.findByUsername(testUser.getUsername());

        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getUserId(), foundUser.get().getUserId());
    }

    @Test
    void findById_ShouldReturnEmpty_WhenUserNotExists() throws SQLException {
        Optional<UserEntity> result = userRepository.findById(999999L);

        assertFalse(result.isPresent());
    }

    @Test
    void findByUsername_ShouldReturnEmpty_ForNonExistingUser() throws SQLException {
        Optional<UserEntity> result = userRepository.findByUsername("non_existing_user");

        assertFalse(result.isPresent());
    }
}