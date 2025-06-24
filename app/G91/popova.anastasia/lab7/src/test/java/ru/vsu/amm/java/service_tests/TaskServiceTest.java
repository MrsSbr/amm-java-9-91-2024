package ru.vsu.amm.java.service_tests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.Mock;
import ru.vsu.amm.java.entities.Task;
import ru.vsu.amm.java.service.TaskService;
import ru.vsu.amm.java.repository.TaskRepository;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doAnswer;

@ExtendWith(MockitoExtension.class)
public class TaskServiceTest {
    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private UUID taskID;
    private UUID columnID;
    private Task task;

    @BeforeEach
    void setUp() {
        taskID = UUID.randomUUID();
        columnID = UUID.randomUUID();
        task = new Task();
        task.setTaskID(taskID);
        task.setColumnID(columnID);
        task.setTaskTitle("TEST TASK");
        task.setTaskDescription("DESCRIPTION");
        task.setStartDate(LocalDate.now());
        task.setEndDate(LocalDate.now().plusDays(1));
    }

    @Test
    void testCreateTask() {
        String title = "NEW TASK";
        String description = "NEW DESCRIPTION";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(2);

        doAnswer(invocation -> {
            Task t = invocation.getArgument(0);
            assertEquals(columnID, t.getColumnID());
            assertEquals(title, t.getTaskTitle());
            assertEquals(description, t.getTaskDescription());
            assertEquals(startDate, t.getStartDate());
            assertEquals(endDate, t.getEndDate());
            return null;
        }).when(taskRepository).save(any(Task.class));

        Task result = taskService.createTask(columnID, title, description, startDate, endDate);

        assertNotNull(result);
        assertEquals(title, result.getTaskTitle());
        assertEquals(columnID, result.getColumnID());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void testCreateTaskWithNullDescriptionDate() {
        String title = "TASK WITH NO DESCRIPTION";

        Task result = taskService.createTask(columnID, title, null, null, null);

        assertNotNull(result);
        assertEquals(title, result.getTaskTitle());
        assertNull(result.getTaskDescription());
        assertEquals(result.getStartDate(), LocalDate.now());
        assertNull(result.getEndDate());
        verify(taskRepository).save(any(Task.class));
    }

    @Test
    void testGetTaskByID() {
        when(taskRepository.getByID(taskID)).thenReturn(task);

        Task result = taskService.getTaskByID(taskID);

        assertNotNull(result);
        assertEquals(taskID, result.getTaskID());
        verify(taskRepository).getByID(taskID);
    }

    @Test
    void getTasksByColumn() {
        List<Task> tasks = Arrays.asList(task);
        when(taskRepository.getByColumn(columnID)).thenReturn(tasks);

        List<Task> result = taskService.getTasksByColumn(columnID);

        assertEquals(1, result.size());
        assertEquals(task, result.get(0));
        verify(taskRepository).getByColumn(columnID);
    }

    @Test
    void testUpdateTaskTitle() {
        String newTitle = "NEW TITLE";
        when(taskRepository.getByID(taskID)).thenReturn(task);

        Task result = taskService.updateTaskTitle(taskID, newTitle);

        assertNotNull(result);
        assertEquals(newTitle, result.getTaskTitle());
        verify(taskRepository).getByID(taskID);
        verify(taskRepository).update(task);
    }

    @Test
    void testMoveToColumn() {
        UUID newColumnID = UUID.randomUUID();
        when(taskRepository.getByID(taskID)).thenReturn(task);

        Task result = taskService.moveToColumn(taskID, newColumnID);

        assertNotNull(result);
        assertEquals(newColumnID, result.getColumnID());
        verify(taskRepository).getByID(taskID);
        verify(taskRepository).update(task);
    }

    @Test
    void testDeleteTask() {
        taskService.deleteTask(taskID);

        verify(taskRepository).delete(taskID);
    }

}
