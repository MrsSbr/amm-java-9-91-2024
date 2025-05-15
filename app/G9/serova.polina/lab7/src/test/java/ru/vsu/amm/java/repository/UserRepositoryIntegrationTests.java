package ru.vsu.amm.java.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UserRepositoryIntegrationTests {

    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepository();
        clearUsersTable();
    }

    @AfterEach
    void tearDown() {
        clearUsersTable();
    }

    @Test
    void crudOperations_shouldWorkCorrectly() {
        UserEntity newUser = new UserEntity();
        newUser.setName("Test");
        newUser.setSurname("Test");
        newUser.setBirthday(LocalDate.now());
        newUser.setEmail("test");
        newUser.setPasswordHash("test");
        newUser.setRole(Role.DOCTOR);
        userRepository.upsert(newUser);

        UserEntity user = userRepository.findAll().getFirst();

        UserEntity found = userRepository.findById(user.getId());
        assertEquals("Test", found.getSurname());

        user.setSurname("Petrov");
        userRepository.update(user);

        UserEntity updated = userRepository.findById(user.getId());
        assertEquals("Petrov", updated.getSurname());

        userRepository.delete(user);
        assertNull(userRepository.findById(user.getId()));
    }

    @Test
    void findByEmail_shouldFindUser() {
        UserEntity user = new UserEntity();
        user.setName("Test");
        user.setSurname("Test");
        user.setBirthday(LocalDate.now());
        user.setEmail("test@email.com");
        user.setPasswordHash("test");
        user.setRole(Role.DOCTOR);

        userRepository.upsert(user);

        UserEntity found = userRepository.findByEmail("test@email.com");
        assertNotNull(found);
        assertEquals("Test", found.getSurname());
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        createTestUser("user1@test.com");
        createTestUser("user2@test.com");

        List<UserEntity> users = userRepository.findAll();
        assertEquals(2, users.size());
    }

    private void createTestUser(String email) {
        UserEntity user = new UserEntity();
        user.setName("Test");
        user.setSurname("Test");
        user.setBirthday(LocalDate.now());
        user.setEmail(email);
        user.setPasswordHash("test");
        user.setRole(Role.DOCTOR);
        userRepository.upsert(user);
    }

    private void clearUsersTable() {
        List<UserEntity> users = userRepository.findAll();
        if (users != null) {
            users.forEach(userRepository::delete);
        }
    }
}