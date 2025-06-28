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
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.amm.java.entities.Board;
import ru.vsu.amm.java.entities.Column;
import ru.vsu.amm.java.entities.Task;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.service.BoardService;
import ru.vsu.amm.java.service.ColumnService;
import ru.vsu.amm.java.service.TaskService;
import ru.vsu.amm.java.servlets.BoardServlet;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

//не работает

@ExtendWith(MockitoExtension.class)
public class BoardServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private BoardService boardService;

    @Mock
    private ColumnService columnService;

    @Mock
    private TaskService taskService;

    @InjectMocks
    private BoardServlet boardServlet;

    private User user;
    private UUID userID;
    private Board board;
    private UUID boardID;
    private Column column;
    private UUID columnID;
    private Task task;
    private UUID taskID;

    /*
    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        userID = UUID.randomUUID();
        user = new User();
        user.setUserID(userID);

        boardID = UUID.randomUUID();
        board = new Board();
        board.setBoardID(boardID);
        board.setUserID(userID);
        board.setBoardTitle("Test Board");

        columnID = UUID.randomUUID();
        column = new Column();
        column.setColumnID(columnID);
        column.setBoardID(boardID);

        taskID = UUID.randomUUID();
        task = new Task();
        task.setTaskID(taskID);
        task.setColumnID(columnID);

        Field boardServiceField = BoardServlet.class.getDeclaredField("boardService");
        boardServiceField.setAccessible(true);
        boardServiceField.set(boardServlet, boardService);

        Field columnServiceField = BoardServlet.class.getDeclaredField("columnService");
        columnServiceField.setAccessible(true);
        columnServiceField.set(boardServlet, columnService);

        Field taskServiceField = BoardServlet.class.getDeclaredField("taskService");
        taskServiceField.setAccessible(true);
        taskServiceField.set(boardServlet, taskService);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getContextPath()).thenReturn("");
    }
    */

    @BeforeEach
    void setUp() throws NoSuchFieldException, IllegalAccessException {
        userID = UUID.randomUUID();
        user = new User();
        user.setUserID(userID);

        boardID = UUID.randomUUID();
        board = new Board();
        board.setBoardID(boardID);
        board.setUserID(userID);
        board.setBoardTitle("Test Board");

        columnID = UUID.randomUUID();
        column = new Column();
        column.setColumnID(columnID);
        column.setBoardID(boardID);

        taskID = UUID.randomUUID();
        task = new Task();
        task.setTaskID(taskID);
        task.setColumnID(columnID);

        Field boardServiceField = BoardServlet.class.getDeclaredField("boardService");
        boardServiceField.setAccessible(true);
        boardServiceField.set(boardServlet, boardService);

        Field columnServiceField = BoardServlet.class.getDeclaredField("columnService");
        columnServiceField.setAccessible(true);
        columnServiceField.set(boardServlet, columnService);

        Field taskServiceField = BoardServlet.class.getDeclaredField("taskService");
        taskServiceField.setAccessible(true);
        taskServiceField.set(boardServlet, taskService);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
    }


    @Test
    void testDoGetUnauthenticatedRedirectsToLogin() throws IOException, ServletException {
        when(session.getAttribute("user")).thenReturn(null);

        boardServlet.doGet(request, response);

        verify(response).sendRedirect("null/auth/login");
    }

    @Test
    void testDoGetBoardListForwardsToList() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/");
        when(boardService.getAllBoards()).thenReturn(List.of(board));
        when(request.getRequestDispatcher("/WEB-INF/views/boards/list.jsp")).thenReturn(requestDispatcher);

        boardServlet.doGet(request, response);

        verify(boardService).getAllBoards();
        verify(request).setAttribute("boards", List.of(board));
        verify(request).getRequestDispatcher("/WEB-INF/views/boards/list.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoGetCreateForwardsToCreate() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/create");
        when(request.getRequestDispatcher("/WEB-INF/views/boards/create.jsp")).thenReturn(requestDispatcher);

        boardServlet.doGet(request, response);

        verify(request).getRequestDispatcher("/WEB-INF/views/boards/create.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoGetEditSuccessForwardsToEdit() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/" + boardID + "/edit");
        when(boardService.getBoardByID(boardID)).thenReturn(board);
        when(request.getParameter("referer")).thenReturn("/boards");
        when(request.getRequestDispatcher("/WEB-INF/views/boards/edit.jsp")).thenReturn(requestDispatcher);

        boardServlet.doGet(request, response);

        verify(boardService).getBoardByID(boardID);
        verify(request).setAttribute("board", board);
        verify(request).setAttribute("referer", "/boards");
        verify(request).getRequestDispatcher("/WEB-INF/views/boards/edit.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoGetViewSuccessForwardsToView() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/" + boardID);
        when(boardService.getBoardByID(boardID)).thenReturn(board);
        when(columnService.getColumnsByBoard(boardID)).thenReturn(List.of(column));
        when(taskService.getTasksByColumn(columnID)).thenReturn(List.of(task));
        when(request.getRequestDispatcher("/WEB-INF/views/boards/view.jsp")).thenReturn(requestDispatcher);

        boardServlet.doGet(request, response);

        verify(boardService).getBoardByID(boardID);
        verify(columnService).getColumnsByBoard(boardID);
        verify(taskService).getTasksByColumn(columnID);
        verify(request).setAttribute("board", board);
        verify(request).setAttribute("columns", List.of(column));
        verify(request).setAttribute(eq("tasksByColumn"), any(Map.class));
        verify(request).getRequestDispatcher("/WEB-INF/views/boards/view.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPostUnauthenticatedRedirectsToLogin() throws IOException, ServletException {
        when(session.getAttribute("user")).thenReturn(null);

        boardServlet.doPost(request, response);

        verify(response).sendRedirect("null/auth/login");
    }

    @Test
    void testDoPostSuccessRedirectsToBoards() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/create");
        when(request.getParameter("title")).thenReturn("Test Board");
        when(request.getParameter("description")).thenReturn("Description");
        when(boardService.createBoard(userID, "Test Board", "Description")).thenReturn(board);

        boardServlet.doPost(request, response);

        verify(boardService).createBoard(userID, "Test Board", "Description");
        verify(response).sendRedirect("null/boards");
    }

    @Test
    void testDoPutUnauthenticatedRedirectsToLogin() throws IOException, ServletException {
        when(session.getAttribute("user")).thenReturn(null);

        boardServlet.doPut(request, response);

        verify(response).sendRedirect("null/auth/login");
    }

    @Test
    void testDoPutEmptyTitleNoUpdateRedirectsToBoard() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/" + boardID);
        when(boardService.getBoardByID(boardID)).thenReturn(board);
        when(request.getParameter("title")).thenReturn("");
        when(request.getParameter("description")).thenReturn("Updated Description");

        boardServlet.doPut(request, response);

        verify(boardService, never()).updateBoardTitle(any(UUID.class), anyString());
        verify(boardService).updateBoardDescription(boardID, "Updated Description");
        verify(response).sendRedirect("/boards/" + boardID);
    }

    @Test
    void testDoPutSuccessRedirectsToBoard() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/" + boardID);
        when(boardService.getBoardByID(boardID)).thenReturn(board);
        when(request.getParameter("title")).thenReturn("Updated Board");
        when(request.getParameter("description")).thenReturn("Updated Description");

        boardServlet.doPut(request, response);

        verify(boardService).updateBoardTitle(boardID, "Updated Board");
        verify(boardService).updateBoardDescription(boardID, "Updated Description");
        verify(response).sendRedirect("null/boards/" + boardID);
    }

    @Test
    void testDoDeleteUnauthenticatedRedirectsToLogin() throws IOException, ServletException {
        when(session.getAttribute("user")).thenReturn(null);

        boardServlet.doDelete(request, response);

        verify(response).sendRedirect("null/auth/login");
    }

    @Test
    void testDoDeleteSuccessRedirectsToBoards() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/" + boardID);
        when(boardService.getBoardByID(boardID)).thenReturn(board);
        doNothing().when(boardService).deleteBoard(boardID);

        boardServlet.doDelete(request, response);

        verify(boardService).deleteBoard(boardID);
        verify(response).sendRedirect("null/boards");
    }

}
