package ru.vsu.amm.java.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.repository.UserRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTests {

    private UserService userService;
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        userService = new UserService(userRepository);
    }

    @Test
    void getAllUsers_shouldReturnEmptyListWhenRepositoryReturnsNull() {
        when(userRepository.findAll()).thenReturn(null);

        List<UserEntity> result = userService.getAllUsers();

        assertTrue(result.isEmpty());
    }

    @Test
    void getAllUsers_shouldReturnUsersFromRepository() {
        UserEntity testUser = createTestUser(1L);
        when(userRepository.findAll()).thenReturn(List.of(testUser));

        List<UserEntity> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals(1L, result.getFirst().getId());
    }

    @Test
    void getUserById_shouldReturnUserWhenExists() {
        UserEntity testUser = createTestUser(1L);
        when(userRepository.findById(1L)).thenReturn(testUser);

        UserEntity result = userService.getUserById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void getUserById_shouldReturnNullWhenNotExists() {
        when(userRepository.findById(1L)).thenReturn(null);

        UserEntity result = userService.getUserById(1L);

        assertNull(result);
    }

    @Test
    void getUserByEmail_shouldReturnUserWhenExists() {
        UserEntity testUser = createTestUser(1L);
        when(userRepository.findByEmail("test@test.com")).thenReturn(testUser);

        UserEntity result = userService.getUserByEmail("test@test.com");

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void addUser_shouldCallRepositoryUpsert() {
        userService.addUser(
                "Test",
                "User",
                "Testovich",
                LocalDate.of(2000, 1, 1),
                "test@test.com",
                "hash",
                Role.DOCTOR
        );

        verify(userRepository).upsert(any(UserEntity.class));
    }

    private UserEntity createTestUser(Long id) {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setName("Test");
        user.setSurname("User");
        user.setPatronymic("Testovich");
        user.setBirthday(LocalDate.of(2000, 1, 1));
        user.setEmail("test@test.com");
        user.setPasswordHash("hash");
        user.setRole(Role.DOCTOR);
        return user;
    }
}