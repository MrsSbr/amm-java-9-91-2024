package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.vsu.amm.java.entities.Board;
import ru.vsu.amm.java.entities.Column;
import ru.vsu.amm.java.entities.Task;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.BoardRepository;
import ru.vsu.amm.java.repository.ColumnRepository;
import ru.vsu.amm.java.repository.TaskRepository;
import ru.vsu.amm.java.service.BoardService;
import ru.vsu.amm.java.service.ColumnService;
import ru.vsu.amm.java.service.TaskService;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet("/tasks/*")
public class TaskServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger((TaskServlet.class));

    private final TaskService taskService = new TaskService(new TaskRepository());
    private final ColumnService columnService = new ColumnService(new ColumnRepository());
    private final BoardService boardService = new BoardService(new BoardRepository(), new ColumnRepository());

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Processing GET request to /tasks{}", req.getPathInfo());

        User currentUser = getCurrentUserOrRedirect(req, resp);
        if (currentUser == null) return;

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || !pathInfo.startsWith("/")) {
            log.warn("Invalid GET request path: {}", pathInfo);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] parts = pathInfo.split("/");
        if (parts.length != 2) {
            log.warn("Invalid GET request path format: {}", pathInfo);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        handleTaskView(req, resp, currentUser, parts[1]);
    }

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Processing POST request to /tasks{}", req.getPathInfo());

        User currentUser = getCurrentUserOrRedirect(req, resp);
        if (currentUser == null) return;

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/") || pathInfo.equals("/create")) {
            handleTaskCreate(req, resp, currentUser);
        } else {
            log.warn("Invalid POST request path: {}", pathInfo);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Processing PUT request to /tasks{}", req.getPathInfo());

        User currentUser = getCurrentUserOrRedirect(req, resp);
        if (currentUser == null) return;

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || !pathInfo.startsWith("/")) {
            log.warn("Invalid PUT request path: {}", pathInfo);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] parts = pathInfo.split("/");
        if (parts.length != 2) {
            log.warn("Invalid PUT request path format: {}", pathInfo);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        handleTaskUpdate(req, resp, currentUser, parts[1]);
    }

    @Override
    public void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("Processing DELETE request to /tasks{}", req.getPathInfo());

        User currentUser = getCurrentUserOrRedirect(req, resp);
        if (currentUser == null) return;

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || !pathInfo.startsWith("/")) {
            log.warn("Invalid DELETE request path: {}", pathInfo);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        String[] parts = pathInfo.split("/");
        if (parts.length != 2) {
            log.warn("Invalid DELETE request path format: {}", pathInfo);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        handleTaskDelete(req, resp, currentUser, parts[1]);
    }

    private User getCurrentUserOrRedirect(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            log.warn("Unauthorized access attempt to /tasks{}", req.getPathInfo());
            resp.sendRedirect("/auth/login");
        }
        return currentUser;
    }

    private void handleTaskView(HttpServletRequest req, HttpServletResponse resp, User user, String taskIdStr)
            throws ServletException, IOException {
        try {
            UUID taskId = UUID.fromString(taskIdStr);
            log.debug("Fetching task details: {}", taskId);

            Task task = taskService.getTaskByID(taskId);
            if (task == null) {
                log.warn("Task not found: {}", taskId);
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Column column = columnService.getColumnByID(task.getColumnID());
            Board board = boardService.getBoardByID(column.getBoardID());

            if (board == null || !board.getUserID().equals(user.getUserID())) {
                log.warn("Access denied for task {} by user {}", taskId, user.getUserID());
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            log.info("Displaying task: {}", taskId);
            req.setAttribute("task", task);
            req.getRequestDispatcher("/WEB-INF/views/tasks/view.jsp").forward(req, resp);
        } catch (IllegalArgumentException e) {
            log.warn("Invalid task ID format: {}", taskIdStr);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    private void handleTaskCreate(HttpServletRequest req, HttpServletResponse resp, User user)
            throws ServletException, IOException {
        String boardIdStr = req.getParameter("boardId");
        String columnIdStr = req.getParameter("columnId");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String startDateStr = req.getParameter("startDate");
        String endDateStr = req.getParameter("endDate");

        try {
            if (boardIdStr == null || columnIdStr == null || title == null || title.trim().isEmpty()) {
                log.warn("Missing required parameters for task creation by user: {}", user.getUserID());
                req.setAttribute("error", "ID доски, ID колонки и заголовок задачи обязательны");
                forwardToBoardView(req, resp, boardIdStr);
                return;
            }

            UUID boardId = UUID.fromString(boardIdStr);
            UUID columnId = UUID.fromString(columnIdStr);

            Board board = boardService.getBoardByID(boardId);
            if (board == null || !board.getUserID().equals(user.getUserID())) {
                log.warn("Access denied for board {} by user {}", boardId, user.getUserID());
                req.setAttribute("error", "Доска не найдена или доступ запрещен");
                forwardToBoardView(req, resp, boardIdStr);
                return;
            }

            Column column = columnService.getColumnByID(columnId);
            if (column == null || !column.getBoardID().equals(boardId)) {
                log.warn("Column {} not found or doesn't belong to board {}", columnId, boardId);
                req.setAttribute("error", "Колонка не найдена или не принадлежит доске");
                forwardToBoardView(req, resp, boardIdStr);
                return;
            }

            LocalDate startDate = parseDate(startDateStr);
            LocalDate endDate = parseDate(endDateStr);

            log.debug("Creating new task in column: {}", columnId);
            taskService.createTask(columnId, title.trim(), description, startDate, endDate);
            log.info("Successfully created task in column: {}", columnId);
            resp.sendRedirect("/boards/" + boardId);
        } catch (IllegalArgumentException e) {
            log.error("Invalid data format: {}", e.getMessage());
            req.setAttribute("error", "Неверный формат данных: " + e.getMessage());
            forwardToBoardView(req, resp, boardIdStr);
        } catch (Exception e) {
            log.error("Error creating task: {}", e.getMessage(), e);
            req.setAttribute("error", "Ошибка создания задачи: " + e.getMessage());
            forwardToBoardView(req, resp, boardIdStr);
        }
    }

    private void handleTaskUpdate(HttpServletRequest req, HttpServletResponse resp, User user, String taskIdStr)
            throws ServletException, IOException {
        try {
            UUID taskId = UUID.fromString(taskIdStr);
            log.debug("Updating task: {}", taskId);

            Task task = taskService.getTaskByID(taskId);
            if (task == null) {
                log.warn("Task not found: {}", taskId);
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Column column = columnService.getColumnByID(task.getColumnID());
            Board board = boardService.getBoardByID(column.getBoardID());

            if (board == null || !board.getUserID().equals(user.getUserID())) {
                log.warn("Update denied for task {} by user {}", taskId, user.getUserID());
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            String title = req.getParameter("title");
            String description = req.getParameter("description");
            String startDateStr = req.getParameter("startDate");
            String endDateStr = req.getParameter("endDate");
            String newColumnIdStr = req.getParameter("columnId");

            if (title != null && !title.trim().isEmpty()) {
                log.debug("Updating title for task: {}", taskId);
                taskService.updateTaskTitle(taskId, title.trim());
            }

            taskService.updateTaskDescription(taskId, description);

            LocalDate startDate = parseDate(startDateStr);
            if (startDate != null) {
                taskService.updateStartDate(taskId, startDate);
            }

            LocalDate endDate = parseDate(endDateStr);
            if (endDate != null) {
                taskService.updateEndDate(taskId, endDate);
            }

            if (newColumnIdStr != null && !newColumnIdStr.isEmpty()) {
                UUID newColumnId = UUID.fromString(newColumnIdStr);
                Column newColumn = columnService.getColumnByID(newColumnId);
                if (newColumn != null && newColumn.getBoardID().equals(column.getBoardID())) {
                    log.debug("Moving task {} to column {}", taskId, newColumnId);
                    taskService.moveToColumn(taskId, newColumnId);
                }
            }

            log.info("Successfully updated task: {}", taskId);
            resp.sendRedirect("/boards/" + board.getBoardID());
        } catch (IllegalArgumentException e) {
            log.error("Invalid data format for task update: {}", e.getMessage());
            req.setAttribute("error", e.getMessage());
            Task task = taskService.getTaskByID(UUID.fromString(taskIdStr));
            req.setAttribute("task", task);
            req.getRequestDispatcher("/WEB-INF/views/tasks/edit.jsp").forward(req, resp);
        } catch (Exception e) {
            log.error("Error updating task {}: {}", taskIdStr, e.getMessage(), e);
            req.setAttribute("error", e.getMessage());
            Task task = taskService.getTaskByID(UUID.fromString(taskIdStr));
            req.setAttribute("task", task);
            req.getRequestDispatcher("/WEB-INF/views/tasks/edit.jsp").forward(req, resp);
        }
    }

    private void handleTaskDelete(HttpServletRequest req, HttpServletResponse resp, User user, String taskIdStr)
            throws IOException {
        try {
            UUID taskId = UUID.fromString(taskIdStr);
            log.debug("Deleting task: {}", taskId);

            Task task = taskService.getTaskByID(taskId);
            if (task == null) {
                log.warn("Task not found: {}", taskId);
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }

            Column column = columnService.getColumnByID(task.getColumnID());
            Board board = boardService.getBoardByID(column.getBoardID());

            if (board == null || !board.getUserID().equals(user.getUserID())) {
                log.warn("Delete denied for task {} by user {}", taskId, user.getUserID());
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

            taskService.deleteTask(taskId);
            log.info("Successfully deleted task: {}", taskId);
            resp.sendRedirect("/boards/" + board.getBoardID());
        } catch (IllegalArgumentException e) {
            log.warn("Invalid task ID format: {}", taskIdStr);
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        } catch (Exception e) {
            log.error("Error deleting task {}: {}", taskIdStr, e.getMessage(), e);
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private LocalDate parseDate(String dateStr) {
        return dateStr != null && !dateStr.isEmpty() ? LocalDate.parse(dateStr) : null;
    }

    private void forwardToBoardView(HttpServletRequest req, HttpServletResponse resp, String boardIdStr)
            throws ServletException, IOException {
        try {
            UUID boardId = boardIdStr != null ? UUID.fromString(boardIdStr) : null;
            if (boardId != null) {
                Board board = boardService.getBoardByID(boardId);
                if (board != null) {
                    log.debug("Forwarding to board view: {}", boardId);
                    req.setAttribute("board", board);
                    List<Column> columns = columnService.getColumnsByBoard(boardId);
                    Map<UUID, List<Task>> tasksByColumn = new HashMap<>();
                    for (Column column : columns) {
                        tasksByColumn.put(column.getColumnID(), taskService.getTasksByColumn(column.getColumnID()));
                    }
                    req.setAttribute("columns", columns);
                    req.setAttribute("tasksByColumn", tasksByColumn);
                    req.getRequestDispatcher("/WEB-INF/views/boards/view.jsp").forward(req, resp);
                    return;
                }
            }
        } catch (IllegalArgumentException e) {
            log.warn("Invalid board ID format: {}", boardIdStr);
        }
        resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный ID доски");
    }

}