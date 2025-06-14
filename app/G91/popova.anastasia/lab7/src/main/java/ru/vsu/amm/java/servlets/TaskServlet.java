package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
import java.util.UUID;

@WebServlet("/tasks/*")
public class TaskServlet extends HttpServlet {
    private final TaskService taskService = new TaskService(new TaskRepository());
    private final ColumnService columnService = new ColumnService(new ColumnRepository());
    private final BoardService boardService = new BoardService(new BoardRepository());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect("/auth/login");
            return;
        }

        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.startsWith("/")) {
            String[] parts = pathInfo.split("/");
            if (parts.length == 2) {
                UUID taskId = UUID.fromString(parts[1]);
                Task task = taskService.getTaskByID(taskId);
                if (task == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                Column column = columnService.getColumnByID(task.getColumnID());
                Board board = boardService.getBoardByID(column.getBoardID());
                if (board == null || !board.getUserID().equals(currentUser.getUserID())) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                req.setAttribute("task", task);
                req.getRequestDispatcher("/WEB-INF/views/tasks/view.jsp").forward(req, resp);
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect("/auth/login");
            return;
        }

        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/create")) {
            String columnIdStr = req.getParameter("columnId");
            String title = req.getParameter("title");
            String description = req.getParameter("description");
            String startDateStr = req.getParameter("startDate");
            String endDateStr = req.getParameter("endDate");
            if (columnIdStr == null || title == null || title.trim().isEmpty()) {
                req.setAttribute("error", "ID колонки и заголовок задачи обязательны");
                req.getRequestDispatcher("/WEB-INF/views/tasks/create.jsp").forward(req, resp);
                return;
            }
            UUID columnId = UUID.fromString(columnIdStr);
            Column column = columnService.getColumnByID(columnId);
            if (column == null) {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                return;
            }
            Board board = boardService.getBoardByID(column.getBoardID());
            if (board == null || !board.getUserID().equals(currentUser.getUserID())) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
            try {
                LocalDate startDate = startDateStr != null && !startDateStr.isEmpty() ? LocalDate.parse(startDateStr) : null;
                LocalDate endDate = endDateStr != null && !endDateStr.isEmpty() ? LocalDate.parse(endDateStr) : null;
                taskService.createTask(columnId, title.trim(), description, startDate, endDate);
                resp.sendRedirect("/boards/" + board.getBoardID());
            } catch (Exception e) {
                req.setAttribute("error", e.getMessage());
                req.getRequestDispatcher("/WEB-INF/views/tasks/create.jsp").forward(req, resp);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect("/auth/login");
            return;
        }

        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.startsWith("/")) {
            String[] parts = pathInfo.split("/");
            if (parts.length == 2) {
                UUID taskId = UUID.fromString(parts[1]);
                Task task = taskService.getTaskByID(taskId);
                if (task == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                Column column = columnService.getColumnByID(task.getColumnID());
                Board board = boardService.getBoardByID(column.getBoardID());
                if (board == null || !board.getUserID().equals(currentUser.getUserID())) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                String title = req.getParameter("title");
                String description = req.getParameter("description");
                String startDateStr = req.getParameter("startDate");
                String endDateStr = req.getParameter("endDate");
                String newColumnIdStr = req.getParameter("columnId");
                try {
                    if (title != null && !title.trim().isEmpty()) {
                        taskService.updateTaskTitle(taskId, title.trim());
                    }
                    taskService.updateTaskDescription(taskId, description);
                    if (startDateStr != null && !startDateStr.isEmpty()) {
                        taskService.updateStartDate(taskId, LocalDate.parse(startDateStr));
                    }
                    if (endDateStr != null && !endDateStr.isEmpty()) {
                        taskService.updateEndDate(taskId, LocalDate.parse(endDateStr));
                    }
                    if (newColumnIdStr != null && !newColumnIdStr.isEmpty()) {
                        UUID newColumnId = UUID.fromString(newColumnIdStr);
                        Column newColumn = columnService.getColumnByID(newColumnId);
                        if (newColumn != null && newColumn.getBoardID().equals(column.getBoardID())) {
                            taskService.moveToColumn(taskId, newColumnId);
                        }
                    }
                    resp.sendRedirect("/boards/" + board.getBoardID());
                } catch (Exception e) {
                    req.setAttribute("error", e.getMessage());
                    req.setAttribute("task", task);
                    req.getRequestDispatcher("/WEB-INF/views/tasks/edit.jsp").forward(req, resp);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User currentUser = (User) req.getSession().getAttribute("user");
        if (currentUser == null) {
            resp.sendRedirect("/auth/login");
            return;
        }

        String pathInfo = req.getPathInfo();
        if (pathInfo != null && pathInfo.startsWith("/")) {
            String[] parts = pathInfo.split("/");
            if (parts.length == 2) {
                UUID taskId = UUID.fromString(parts[1]);
                Task task = taskService.getTaskByID(taskId);
                if (task == null) {
                    resp.sendError(HttpServletResponse.SC_NOT_FOUND);
                    return;
                }
                Column column = columnService.getColumnByID(task.getColumnID());
                Board board = boardService.getBoardByID(column.getBoardID());
                if (board == null || !board.getUserID().equals(currentUser.getUserID())) {
                    resp.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                try {
                    taskService.deleteTask(taskId);
                    resp.sendRedirect("/boards/" + board.getBoardID());
                } catch (Exception e) {
                    resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            } else {
                resp.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}