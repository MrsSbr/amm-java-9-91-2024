package ru.vsu.amm.java.repository_tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.amm.java.DB_config.DatabaseConnection;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.UserRepository;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserRepositoryTest {
    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement ps;

    @Mock
    private ResultSet rs;

    @InjectMocks
    private UserRepository userRepository;

    private MockedStatic<DatabaseConnection> mockedDatabaseConnection;
    private UUID userID;
    private User user;

    @BeforeEach
    void setUp() {
        userID = UUID.randomUUID();
        user = new User();
        user.setUserID(userID);
        user.setPassword("password123");
        user.setEmail("test@example.com");
        user.setName("Test User");

        mockedDatabaseConnection = mockStatic(DatabaseConnection.class);
        mockedDatabaseConnection.when(DatabaseConnection::getConnection).thenReturn(connection);
    }

    @AfterEach
    void tearDown() {
        mockedDatabaseConnection.close();
    }

    @Test
    void testGetByID() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getObject("UserID")).thenReturn(userID);
        when(rs.getString("Password")).thenReturn("password123");
        when(rs.getString("Email")).thenReturn("test@example.com");
        when(rs.getString("Name")).thenReturn("Test User");

        User result = userRepository.getByID(userID);

        assertNotNull(result);
        assertEquals(userID, result.getUserID());
        assertEquals("password123", result.getPassword());
        assertEquals("test@example.com", result.getEmail());
        assertEquals("Test User", result.getName());
        verify(ps).setObject(1, userID);
        verify(ps).executeQuery();
    }

    @Test
    void testGetByEmail() throws SQLException {
        String email = "test@example.com";
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getObject("UserID")).thenReturn(userID);
        when(rs.getString("Password")).thenReturn("password123");
        when(rs.getString("Email")).thenReturn(email);
        when(rs.getString("Name")).thenReturn("Test User");

        User result = userRepository.getByEmail(email);

        assertNotNull(result);
        assertEquals(userID, result.getUserID());
        assertEquals("password123", result.getPassword());
        assertEquals(email, result.getEmail());
        assertEquals("Test User", result.getName());
        verify(ps).setString(1, email);
        verify(ps).executeQuery();
    }

    @Test
    void testGetAll() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false); // One user
        when(rs.getObject("UserID")).thenReturn(userID);
        when(rs.getString("Password")).thenReturn("password123");
        when(rs.getString("Email")).thenReturn("test@example.com");
        when(rs.getString("Name")).thenReturn("Test User");

        List<User> result = userRepository.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        User resultUser = result.get(0);
        assertEquals(userID, resultUser.getUserID());
        assertEquals("password123", resultUser.getPassword());
        assertEquals("test@example.com", resultUser.getEmail());
        assertEquals("Test User", resultUser.getName());
        verify(ps).executeQuery();
    }

    @Test
    void testSave() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        userRepository.save(user);

        verify(ps).setObject(1, userID);
        verify(ps).setString(2, "password123");
        verify(ps).setString(3, "test@example.com");
        verify(ps).setString(4, "Test User");
        verify(ps).executeUpdate();
    }

    @Test
    void testUpdate() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        userRepository.update(user);

        verify(ps).setString(1, "password123");
        verify(ps).setString(2, "test@example.com");
        verify(ps).setString(3, "Test User");
        verify(ps).setObject(4, userID);
        verify(ps).executeUpdate();
    }

    @Test
    void testDeleteSuccess() throws SQLException {
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeUpdate()).thenReturn(1);

        userRepository.delete(userID);

        verify(ps).setObject(1, userID);
        verify(ps).executeUpdate();
    }

}
