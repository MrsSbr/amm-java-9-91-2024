package ru.vsu.amm.java.repository_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import ru.vsu.amm.java.DB_config.DatabaseConnection;
import ru.vsu.amm.java.entities.Task;
import ru.vsu.amm.java.repository.TaskRepository;
import java.sql.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskRepositoryTest {
    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement ps;

    @Mock
    private ResultSet rs;

    private  TaskRepository taskRepository;

    private  UUID taskID;
    private  UUID columnID;
    private Task task;

    @BeforeEach
    void setUp() throws SQLException {
        taskID = UUID.randomUUID();
        columnID = UUID.randomUUID();
        task = new Task();
        task.setTaskID(taskID);
        task.setColumnID(columnID);
        task.setTaskTitle("TEST TASK");
        task.setTaskDescription("DESCRIPTION");
        task.setStartDate(LocalDate.now());
        task.setEndDate(LocalDate.now().plusDays(1));

        mockStatic(DatabaseConnection.class);
        when(DatabaseConnection.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        taskRepository = new TaskRepository();
    }

    @Test
    void testGetByID() throws SQLException {
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getObject("TaskID")).thenReturn(taskID);
        when(rs.getObject("ColumnID")).thenReturn(columnID);
        when(rs.getString("TaskTitle")).thenReturn("TEST TASK");
        when(rs.getString("TaskDescription")).thenReturn("DESCRIPTION");
        when(rs.getDate("StartDate")).thenReturn(java.sql.Date.valueOf(LocalDate.now()));
        when(rs.getDate("EndDate")).thenReturn(java.sql.Date.valueOf(LocalDate.now()
                .plusDays(1)));

        Task result = taskRepository.getByID(taskID);

        assertNotNull(result);
        assertEquals(taskID, result.getTaskID());
        assertEquals(columnID, result.getColumnID());
        assertEquals("TEST TASK", result.getTaskTitle());
        verify(ps).setObject(1, taskID);
        verify(ps).executeQuery();
    }

    @Test
    void testSave() throws SQLException {
        when(ps.executeUpdate()).thenReturn(1);

        taskRepository.save(task);

        verify(ps).setObject(1, task.getTaskID());
        verify(ps).setObject(2, task.getColumnID());
        verify(ps).setString(3, task.getTaskTitle());
        verify(ps).setString(4, task.getTaskDescription());
        verify(ps).setDate(5, Date.valueOf(task.getStartDate()));
        verify(ps).setDate(6, Date.valueOf(task.getEndDate()));
        verify(ps).executeUpdate();
    }

    @Test
    void testGetByColumn() throws  SQLException {
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true, false);
        when(rs.getObject("TaskID")).thenReturn(taskID);
        when(rs.getObject("ColumnID")).thenReturn(columnID);
        when(rs.getString("TaskTitle")).thenReturn("TEST TASK");
        when(rs.getString("TaskDescription")).thenReturn("DESCRIPTION");
        when(rs.getDate("StartDate")).thenReturn(java.sql.Date.valueOf(LocalDate.now()));
        when(rs.getDate("EndDate")).thenReturn(java.sql.Date.valueOf(LocalDate.now()
                .plusDays(1)));

        List<Task> result = taskRepository.getByColumn(columnID);

        assertEquals(1, result.size());
        assertEquals(taskID, result.get(0).getTaskID());
        verify(ps).setObject(1, columnID);
        verify(ps).executeQuery();
    }

    @Test
    void testDelete() throws SQLException {
        when(ps.executeUpdate()).thenReturn(1);

        taskRepository.delete(taskID);

        verify(ps).setObject(1, taskID);
        verify(ps).executeUpdate();
    }

    @Test
    void testGetAllEmpty() throws SQLException {
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);

        List<Task> result = taskRepository.getAll();

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(ps).executeQuery();
    }

}
