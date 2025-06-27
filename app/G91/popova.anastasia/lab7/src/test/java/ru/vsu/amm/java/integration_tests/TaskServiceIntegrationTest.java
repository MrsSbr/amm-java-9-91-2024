package ru.vsu.amm.java.integration_tests;

import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entities.Task;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.BoardRepository;
import ru.vsu.amm.java.repository.ColumnRepository;
import ru.vsu.amm.java.repository.TaskRepository;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.BoardService;
import ru.vsu.amm.java.service.ColumnService;
import ru.vsu.amm.java.service.TaskService;
import ru.vsu.amm.java.service.UserService;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskServiceIntegrationTest extends BaseIntegrationTest {

    private TaskService createTaskService() {
        return new TaskService(new TaskRepository());
    }

    private UUID createTestColumn() {
        UserService userService = new UserService(new UserRepository());
        User user = userService.register("taskuser", "taskuser@example.com", "password123");

        BoardService boardService = new BoardService(new BoardRepository(), new ColumnRepository());
        UUID boardID = boardService.createBoard(user.getUserID(), "Test Board", "Description").getBoardID();

        ColumnService columnService = new ColumnService(new ColumnRepository());
        return columnService.createColumn(boardID, "Test Column").getColumnID();
    }

    @Test
    void createTaskTest() {
        TaskService service = createTaskService();
        UUID columnID = createTestColumn();

        Task task = service.createTask(columnID, "New Task", "Description",
                LocalDate.now(), LocalDate.now().plusDays(1));

        assertNotNull(task.getTaskID());
        assertEquals("New Task", task.getTaskTitle());
    }

    @Test
    void getTaskByIDTest() {
        TaskService service = createTaskService();
        UUID columnID = createTestColumn();
        Task created = service.createTask(columnID, "Test Task", null,
                LocalDate.now(), null);

        Task found = service.getTaskByID(created.getTaskID());

        assertEquals(created.getTaskID(), found.getTaskID());
    }

    @Test
    void getTasksByColumnTest() {
        TaskService service = createTaskService();
        UUID columnID = createTestColumn();
        service.createTask(columnID, "Task 1", null, LocalDate.now(), null);
        service.createTask(columnID, "Task 2", "Desc", LocalDate.now(), LocalDate.now().plusDays(1));

        List<Task> tasks = service.getTasksByColumn(columnID);

        assertEquals(2, tasks.size());
    }

    @Test
    void updateTaskTitleTest() {
        TaskService service = createTaskService();
        UUID columnID = createTestColumn();
        Task task = service.createTask(columnID, "Old Title", null, LocalDate.now(), null);

        Task updated = service.updateTaskTitle(task.getTaskID(), "New Title");

        assertEquals("New Title", updated.getTaskTitle());
    }

    @Test
    void deleteTaskTest() {
        TaskService service = createTaskService();
        UUID columnID = createTestColumn();
        Task task = service.createTask(columnID, "To Delete", null, LocalDate.now(), null);

        service.deleteTask(task.getTaskID());

        assertNull(service.getTaskByID(task.getTaskID()));
    }

}