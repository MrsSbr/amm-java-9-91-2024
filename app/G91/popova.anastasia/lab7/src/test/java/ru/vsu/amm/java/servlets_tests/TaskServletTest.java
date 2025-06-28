package ru.vsu.amm.java.servlets_tests;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import  org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.amm.java.entities.Board;
import ru.vsu.amm.java.entities.Column;
import ru.vsu.amm.java.entities.Task;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.service.BoardService;
import ru.vsu.amm.java.service.ColumnService;
import ru.vsu.amm.java.service.TaskService;
import ru.vsu.amm.java.servlets.TaskServlet;

import java.io.IOException;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TaskServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private TaskService taskService;

    @Mock
    private ColumnService columnService;

    @Mock
    private BoardService boardService;

    @InjectMocks
    private TaskServlet taskServlet;

    private User user;
    private UUID userID;
    private Board board;
    private UUID boardID;
    private Column column;
    private UUID columnID;
    private Task task;
    private UUID taskID;

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        userID = UUID.randomUUID();
        user = new User();
        user.setUserID(userID);

        boardID = UUID.randomUUID();
        board = new Board();
        board.setBoardID(boardID);
        board.setUserID(userID);

        columnID = UUID.randomUUID();
        column = new Column();
        column.setColumnID(columnID);
        column.setBoardID(boardID);

        taskID = UUID.randomUUID();
        task = new Task();
        task.setTaskID(taskID);
        task.setColumnID(columnID);
        task.setTaskTitle("Test Task");

        Field taskServiceField = TaskServlet.class.getDeclaredField("taskService");
        taskServiceField.setAccessible(true);
        taskServiceField.set(taskServlet, taskService);

        Field columnServiceField = TaskServlet.class.getDeclaredField("columnService");
        columnServiceField.setAccessible(true);
        columnServiceField.set(taskServlet, columnService);

        Field boardServiceField = TaskServlet.class.getDeclaredField("boardService");
        boardServiceField.setAccessible(true);
        boardServiceField.set(taskServlet, boardService);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
    }

    @Test
    void testDoGetUnauthenticatedRedirectsToLogin() throws IOException, ServletException {
        when(session.getAttribute("user")).thenReturn(null);

        taskServlet.doGet(request, response);

        verify(response).sendRedirect("/auth/login");
    }

    @Test
    void testDoGetInvalidPathReturnsBadRequest() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn(null);

        taskServlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void testDoGetInvalidPathFormatReturnsNotFound() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/invalid/path");

        taskServlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDoGetInvalidTaskIdFormatReturnsBadRequest() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/invalid");

        taskServlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void testDoGetTaskNotFoundReturnsNotFound() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/" + taskID);
        when(taskService.getTaskByID(taskID)).thenReturn(null);

        taskServlet.doGet(request, response);

        verify(taskService).getTaskByID(taskID);
        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDoGetAccessDeniedReturnsForbidden() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/" + taskID);
        when(taskService.getTaskByID(taskID)).thenReturn(task);
        when(columnService.getColumnByID(columnID)).thenReturn(column);
        when(boardService.getBoardByID(boardID)).thenReturn(null);

        taskServlet.doGet(request, response);

        verify(taskService).getTaskByID(taskID);
        verify(columnService).getColumnByID(columnID);
        verify(boardService).getBoardByID(boardID);
        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void testDoGetSuccessForwardsToView() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/" + taskID);
        when(taskService.getTaskByID(taskID)).thenReturn(task);
        when(columnService.getColumnByID(columnID)).thenReturn(column);
        when(boardService.getBoardByID(boardID)).thenReturn(board);
        when(request.getRequestDispatcher("/WEB-INF/views/tasks/view.jsp")).thenReturn(requestDispatcher);

        taskServlet.doGet(request, response);

        verify(taskService).getTaskByID(taskID);
        verify(columnService).getColumnByID(columnID);
        verify(boardService).getBoardByID(boardID);
        verify(request).setAttribute("task", task);
        verify(request).getRequestDispatcher("/WEB-INF/views/tasks/view.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPostUnauthenticatedRedirectsToLogin() throws IOException, ServletException {
        when(session.getAttribute("user")).thenReturn(null);

        taskServlet.doPost(request, response);

        verify(response).sendRedirect("/auth/login");
    }

    @Test
    void testDoPostInvalidPathReturnsNotFound() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/invalid");

        taskServlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDoPostMissingParametersForwardsWithError() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/create");
        when(request.getParameter("boardId")).thenReturn(null);

        taskServlet.doPost(request, response);

        verify(request).setAttribute("error", "ID доски, ID колонки и заголовок задачи обязательны");
        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный ID доски");
    }

    @Test
    void testDoPostInvalidBoardIdFormatForwardsWithError() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/create");
        when(request.getParameter("boardId")).thenReturn("invalid");
        when(request.getParameter("columnId")).thenReturn(columnID.toString());
        when(request.getParameter("title")).thenReturn("Test Task");

        taskServlet.doPost(request, response);

        verify(request).setAttribute(eq("error"), startsWith("Неверный формат данных: "));
        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST, "Неверный ID доски");
    }

    @Test
    void testDoPostColumnNotFoundForwardsWithError() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/create");
        when(request.getParameter("boardId")).thenReturn(boardID.toString());
        when(request.getParameter("columnId")).thenReturn(columnID.toString());
        when(request.getParameter("title")).thenReturn("Test Task");
        when(boardService.getBoardByID(boardID)).thenReturn(board);
        when(columnService.getColumnByID(columnID)).thenReturn(null);
        when(request.getRequestDispatcher("/WEB-INF/views/boards/view.jsp")).thenReturn(requestDispatcher);

        taskServlet.doPost(request, response);

        verify(boardService, atLeastOnce()).getBoardByID(boardID);
        verify(columnService).getColumnByID(columnID);
        verify(request).setAttribute("error", "Колонка не найдена или не принадлежит доске");
        verify(request).getRequestDispatcher("/WEB-INF/views/boards/view.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPostSuccessRedirectsToBoard() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/create");
        when(request.getParameter("boardId")).thenReturn(boardID.toString());
        when(request.getParameter("columnId")).thenReturn(columnID.toString());
        when(request.getParameter("title")).thenReturn("Test Task");
        when(request.getParameter("description")).thenReturn("Description");
        when(request.getParameter("startDate")).thenReturn("2025-06-20");
        when(request.getParameter("endDate")).thenReturn("2025-06-25");
        when(boardService.getBoardByID(boardID)).thenReturn(board);
        when(columnService.getColumnByID(columnID)).thenReturn(column);
        when(taskService.createTask(eq(columnID), eq("Test Task"), eq("Description"), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(task);

        taskServlet.doPost(request, response);

        verify(taskService).createTask(eq(columnID), eq("Test Task"), eq("Description"), any(LocalDate.class), any(LocalDate.class));
        verify(response).sendRedirect("/boards/" + boardID);
    }

    @Test
    void testDoPutUnauthenticatedRedirectsToLogin() throws IOException, ServletException {
        when(session.getAttribute("user")).thenReturn(null);

        taskServlet.doPut(request, response);

        verify(response).sendRedirect("/auth/login");
    }

    @Test
    void testDoPutInvalidPathReturnsBadRequest() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn(null);

        taskServlet.doPut(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void testDoPutTaskNotFoundReturnsNotFound() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/" + taskID);
        when(taskService.getTaskByID(taskID)).thenReturn(null);

        taskServlet.doPut(request, response);

        verify(taskService).getTaskByID(taskID);
        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDoPutAccessDeniedReturnsForbidden() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/" + taskID);
        when(taskService.getTaskByID(taskID)).thenReturn(task);
        when(columnService.getColumnByID(columnID)).thenReturn(column);
        when(boardService.getBoardByID(boardID)).thenReturn(null);

        taskServlet.doPut(request, response);

        verify(taskService).getTaskByID(taskID);
        verify(columnService).getColumnByID(columnID);
        verify(boardService).getBoardByID(boardID);
        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void testDoPutSuccessRedirectsToBoard() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/" + taskID);
        when(taskService.getTaskByID(taskID)).thenReturn(task);
        when(columnService.getColumnByID(columnID)).thenReturn(column);
        when(boardService.getBoardByID(boardID)).thenReturn(board);
        when(request.getParameter("title")).thenReturn("Updated Task");
        when(request.getParameter("description")).thenReturn("Updated Description");
        when(request.getParameter("startDate")).thenReturn("2025-06-21");
        when(request.getParameter("endDate")).thenReturn("2025-06-26");
        when(request.getParameter("columnId")).thenReturn(columnID.toString());
        when(columnService.getColumnByID(columnID)).thenReturn(column);

        taskServlet.doPut(request, response);

        verify(taskService).updateTaskTitle(taskID, "Updated Task");
        verify(taskService).updateTaskDescription(taskID, "Updated Description");
        verify(taskService).updateStartDate(eq(taskID), any(LocalDate.class));
        verify(taskService).updateEndDate(eq(taskID), any(LocalDate.class));
        verify(taskService).moveToColumn(taskID, columnID);
        verify(response).sendRedirect("/boards/" + boardID);
    }

    @Test
    void testDoDeleteUnauthenticatedRedirectsToLogin() throws IOException, ServletException {
        when(session.getAttribute("user")).thenReturn(null);

        taskServlet.doDelete(request, response);

        verify(response).sendRedirect("/auth/login");
    }

    @Test
    void testDoDeleteInvalidPathReturnsBadRequest() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn(null);

        taskServlet.doDelete(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void testDoDeleteInvalidTaskIdFormatReturnsBadRequest() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/invalid");

        taskServlet.doDelete(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void testDoDeleteTaskNotFoundReturnsNotFound() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/" + taskID);
        when(taskService.getTaskByID(taskID)).thenReturn(null);

        taskServlet.doDelete(request, response);

        verify(taskService).getTaskByID(taskID);
        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDoDeleteAccessDeniedReturnsForbidden() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/" + taskID);
        when(taskService.getTaskByID(taskID)).thenReturn(task);
        when(columnService.getColumnByID(columnID)).thenReturn(column);
        when(boardService.getBoardByID(boardID)).thenReturn(null);

        taskServlet.doDelete(request, response);

        verify(taskService).getTaskByID(taskID);
        verify(columnService).getColumnByID(columnID);
        verify(boardService).getBoardByID(boardID);
        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void testDoDeleteSuccessRedirectsToBoard() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/" + taskID);
        when(taskService.getTaskByID(taskID)).thenReturn(task);
        when(columnService.getColumnByID(columnID)).thenReturn(column);
        when(boardService.getBoardByID(boardID)).thenReturn(board);
        doNothing().when(taskService).deleteTask(taskID);

        taskServlet.doDelete(request, response);

        verify(taskService).deleteTask(taskID);
        verify(response).sendRedirect("/boards/" + boardID);
    }

}
