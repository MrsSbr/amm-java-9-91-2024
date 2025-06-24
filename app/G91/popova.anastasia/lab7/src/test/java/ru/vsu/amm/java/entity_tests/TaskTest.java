package ru.vsu.amm.java.entity_tests;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.Task;
import java.time.LocalDate;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;

public class TaskTest {

    @Test
    void testCreateTask() {
        Task task = new Task();
        assertNotNull(task.getTaskID());
        assertTrue(task.getTaskID() instanceof UUID);
    }

    @Test
    void testTaskGettersAndSetters() {
        Task task = new Task();
        UUID taskId = UUID.randomUUID();
        UUID columnId = UUID.randomUUID();
        String title = "TEST TASK";
        String description = "DESCRIPTION";
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = LocalDate.now().plusDays(1);

        task.setTaskID(taskId);
        task.setColumnID(columnId);
        task.setTaskTitle(title);
        task.setTaskDescription(description);
        task.setStartDate(startDate);
        task.setEndDate(endDate);

        assertEquals(taskId, task.getTaskID());
        assertEquals(columnId, task.getColumnID());
        assertEquals(title, task.getTaskTitle());
        assertEquals(description, task.getTaskDescription());
        assertEquals(startDate, task.getStartDate());
        assertEquals(endDate, task.getEndDate());
    }

}
