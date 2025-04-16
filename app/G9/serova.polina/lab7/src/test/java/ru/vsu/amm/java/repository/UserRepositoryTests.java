package ru.vsu.amm.java.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import ru.vsu.amm.java.configuration.DatabaseConfiguration;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserRepositoryTests {

    private UserRepository userRepository;
    private Connection mockConnection;
    private PreparedStatement mockStatement;
    private ResultSet mockResultSet;

    @BeforeEach
    void setUp() throws SQLException {
        DataSource mockDataSource = mock(DataSource.class);
        mockConnection = mock(Connection.class);
        mockStatement = mock(PreparedStatement.class);
        mockResultSet = mock(ResultSet.class);

        when(mockDataSource.getConnection()).thenReturn(mockConnection);
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockStatement);
        when(mockStatement.executeQuery()).thenReturn(mockResultSet);

        try (MockedStatic<DatabaseConfiguration> mocked = mockStatic(DatabaseConfiguration.class)) {
            mocked.when(DatabaseConfiguration::getDataSource).thenReturn(mockDataSource);
            userRepository = new UserRepository();
        }
    }

    @Test
    void findById_shouldReturnUserWhenExists() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        mockUserResultSet(mockResultSet, 1L, "Ivan", "Ivanov", "Ivanovich",
                LocalDate.of(1990, 1, 1), "ivan@test.com", "hash", "DOCTOR");

        UserEntity result = userRepository.findById(1L);

        assertNotNull(result);
        assertEquals("Ivan", result.getName());
        verify(mockStatement).setLong(1, 1L);
    }

    @Test
    void findById_shouldReturnNullWhenNotExists() throws SQLException {
        when(mockResultSet.next()).thenReturn(false);

        UserEntity result = userRepository.findById(1L);

        assertNull(result);
    }

    @Test
    void findByEmail_shouldReturnUserWhenExists() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(false);
        mockUserResultSet(mockResultSet, 1L, "Ivan", "Ivanov", "Ivanovich",
                LocalDate.of(1990, 1, 1), "ivan@test.com", "hash", "DOCTOR");

        UserEntity result = userRepository.findByEmail("ivan@test.com");

        assertNotNull(result);
        assertEquals("ivan@test.com", result.getEmail());
        verify(mockStatement).setString(1, "ivan@test.com");
    }

    @Test
    void findAll_shouldReturnAllUsers() throws SQLException {
        when(mockResultSet.next()).thenReturn(true).thenReturn(true).thenReturn(false);
        mockUserResultSet(mockResultSet, 1L, "Ivan", "Ivanov", "Ivanovich",
                LocalDate.of(1990, 1, 1), "ivan@test.com", "hash", "DOCTOR");
        mockUserResultSet(mockResultSet, 2L, "Petr", "Petrov", "Petrovich",
                LocalDate.of(1985, 5, 5), "petr@test.com", "hash2", "PATIENT");

        List<UserEntity> result = userRepository.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void upsert_shouldInsertNewUser() throws SQLException {
        UserEntity user = createTestUser();

        userRepository.upsert(user);

        verify(mockConnection).prepareStatement(contains("INSERT INTO"));
        verify(mockStatement).executeUpdate();
    }

    @Test
    void update_shouldUpdateExistingUser() throws SQLException {
        UserEntity user = createTestUser();
        user.setId(1L);

        userRepository.update(user);

        verify(mockConnection).prepareStatement(contains("UPDATE"));
        verify(mockStatement).setLong(8, 1L);
        verify(mockStatement).executeUpdate();
    }

    @Test
    void delete_shouldRemoveUser() throws SQLException {
        UserEntity user = createTestUser();
        user.setId(1L);

        userRepository.delete(user);

        verify(mockConnection).prepareStatement(contains("DELETE"));
        verify(mockStatement).setLong(1, 1L);
        verify(mockStatement).executeUpdate();
    }

    private void mockUserResultSet(ResultSet rs, long id, String name, String surname,
                                   String patronymic, LocalDate birthday, String email, String passwordHash,
                                   String role) throws SQLException {
        when(rs.getLong("id")).thenReturn(id);
        when(rs.getString("name")).thenReturn(name);
        when(rs.getString("surname")).thenReturn(surname);
        when(rs.getString("patronymic")).thenReturn(patronymic);
        when(rs.getDate("birthday")).thenReturn(Date.valueOf(birthday));
        when(rs.getString("email")).thenReturn(email);
        when(rs.getString("password_hash")).thenReturn(passwordHash);
        when(rs.getString("role")).thenReturn(role);
    }

    private UserEntity createTestUser() {
        UserEntity user = new UserEntity();
        user.setName("Test");
        user.setSurname("Test");
        user.setBirthday(LocalDate.now());
        user.setEmail("test");
        user.setPasswordHash("test");
        user.setRole(Role.DOCTOR);
        return user;
    }
}