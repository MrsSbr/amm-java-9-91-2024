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
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.service.BoardService;
import ru.vsu.amm.java.service.ColumnService;
import ru.vsu.amm.java.servlets.ColumnServlet;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.startsWith;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ColumnServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private ColumnService columnService;

    @Mock
    private BoardService boardService;

    @InjectMocks
    private ColumnServlet columnServlet;

    private User user;
    private UUID userID;
    private Board board;
    private UUID boardID;
    private Column column;
    private UUID columnID;

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
        column.setColumnTitle("Test Column");

        Field columnServiceField = ColumnServlet.class.getDeclaredField("columnService");
        columnServiceField.setAccessible(true);
        columnServiceField.set(columnServlet, columnService);

        Field boardServiceField = ColumnServlet.class.getDeclaredField("boardService");
        boardServiceField.setAccessible(true);
        boardServiceField.set(columnServlet, boardService);

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
    }

    @Test
    void testDoPostUnauthenticatedRedirectsToLogin() throws IOException, ServletException {
        when(session.getAttribute("user")).thenReturn(null);

        columnServlet.doPost(request, response);

        verify(response).sendRedirect("/auth/login");
    }

    @Test
    void testDoPostInvalidPathReturnsNotFound() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/invalid");

        columnServlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDoPostMissingParametersForwardsWithError() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/create");
        when(request.getParameter("boardId")).thenReturn(null);
        when(request.getParameter("title")).thenReturn("");
        when(request.getRequestDispatcher("/WEB-INF/views/columns/create.jsp")).thenReturn(requestDispatcher);

        columnServlet.doPost(request, response);

        verify(request).setAttribute("error", "ID доски и заголовок колонки обязательны");
        verify(request).getRequestDispatcher("/WEB-INF/views/columns/create.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPostInvalidBoardIdFormatForwardsWithError() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/create");
        when(request.getParameter("boardId")).thenReturn("invalid");
        when(request.getParameter("title")).thenReturn("Test Column");
        when(request.getRequestDispatcher("/WEB-INF/views/columns/create.jsp")).thenReturn(requestDispatcher);

        columnServlet.doPost(request, response);

        verify(request).setAttribute(eq("error"), startsWith("Invalid UUID string"));
        verify(request).getRequestDispatcher("/WEB-INF/views/columns/create.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPostBoardNotFoundReturnsForbidden() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/create");
        when(request.getParameter("boardId")).thenReturn(boardID.toString());
        when(request.getParameter("title")).thenReturn("Test Column");
        when(boardService.getBoardByID(boardID)).thenReturn(null);

        columnServlet.doPost(request, response);

        verify(boardService).getBoardByID(boardID);
        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void testDoPostAccessDeniedReturnsForbidden() throws ServletException, IOException {
        Board unauthorizedBoard = new Board();
        unauthorizedBoard.setBoardID(boardID);
        unauthorizedBoard.setUserID(UUID.randomUUID());

        when(request.getPathInfo()).thenReturn("/create");
        when(request.getParameter("boardId")).thenReturn(boardID.toString());
        when(request.getParameter("title")).thenReturn("Test Column");
        when(boardService.getBoardByID(boardID)).thenReturn(unauthorizedBoard);

        columnServlet.doPost(request, response);

        verify(boardService).getBoardByID(boardID);
        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void testDoPostSuccessRedirectsToBoard() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/create");
        when(request.getParameter("boardId")).thenReturn(boardID.toString());
        when(request.getParameter("title")).thenReturn("Test Column");
        when(boardService.getBoardByID(boardID)).thenReturn(board);
        when(columnService.createColumn(boardID, "Test Column")).thenReturn(column);

        columnServlet.doPost(request, response);

        verify(columnService).createColumn(boardID, "Test Column");
        verify(response).sendRedirect("/boards/" + boardID);
    }

    @Test
    void testDoPutUnauthenticatedRedirectsToLogin() throws IOException, ServletException {
        when(session.getAttribute("user")).thenReturn(null);

        columnServlet.doPut(request, response);

        verify(response).sendRedirect("/auth/login");
    }

    @Test
    void testDoPutInvalidPathReturnsBadRequest() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn(null);

        columnServlet.doPut(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void testDoPutInvalidPathFormatReturnsNotFound() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/invalid/path");

        columnServlet.doPut(request, response);

        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDoPutColumnNotFoundReturnsNotFound() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/" + columnID);
        when(columnService.getColumnByID(columnID)).thenReturn(null);

        columnServlet.doPut(request, response);

        verify(columnService).getColumnByID(columnID);
        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDoPutAccessDeniedReturnsForbidden() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/" + columnID);
        when(columnService.getColumnByID(columnID)).thenReturn(column);
        when(boardService.getBoardByID(boardID)).thenReturn(null);

        columnServlet.doPut(request, response);

        verify(columnService).getColumnByID(columnID);
        verify(boardService).getBoardByID(boardID);
        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void testDoPutEmptyTitleNoUpdateRedirectsToBoard() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/" + columnID);
        when(columnService.getColumnByID(columnID)).thenReturn(column);
        when(boardService.getBoardByID(boardID)).thenReturn(board);
        when(request.getParameter("title")).thenReturn("");

        columnServlet.doPut(request, response);

        verify(columnService, never()).updateColumnTitle(any(UUID.class), anyString());
        verify(response).sendRedirect("/boards/" + boardID);
    }

    @Test
    void testDoPutSuccessRedirectsToBoard() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/" + columnID);
        when(columnService.getColumnByID(columnID)).thenReturn(column);
        when(boardService.getBoardByID(boardID)).thenReturn(board);
        when(request.getParameter("title")).thenReturn("Updated Column");

        columnServlet.doPut(request, response);

        verify(columnService).updateColumnTitle(columnID, "Updated Column");
        verify(response).sendRedirect("/boards/" + boardID);
    }

    @Test
    void testDoDeleteUnauthenticatedRedirectsToLogin() throws IOException, ServletException {
        when(session.getAttribute("user")).thenReturn(null);

        columnServlet.doDelete(request, response);

        verify(response).sendRedirect("/auth/login");
    }

    @Test
    void testDoDeleteInvalidPathReturnsBadRequest() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn(null);

        columnServlet.doDelete(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void testDoDeleteInvalidPathFormatReturnsNotFound() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/invalid/path");

        columnServlet.doDelete(request, response);

        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDoDeleteColumnNotFoundReturnsNotFound() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/" + columnID);
        when(columnService.getColumnByID(columnID)).thenReturn(null);

        columnServlet.doDelete(request, response);

        verify(columnService).getColumnByID(columnID);
        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDoDeleteAccessDeniedReturnsForbidden() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/" + columnID);
        when(columnService.getColumnByID(columnID)).thenReturn(column);
        when(boardService.getBoardByID(boardID)).thenReturn(null);

        columnServlet.doDelete(request, response);

        verify(columnService).getColumnByID(columnID);
        verify(boardService).getBoardByID(boardID);
        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void testDoDeleteSuccessRedirectsToBoard() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/" + columnID);
        when(columnService.getColumnByID(columnID)).thenReturn(column);
        when(boardService.getBoardByID(boardID)).thenReturn(board);
        doNothing().when(columnService).deleteColumn(columnID);

        columnServlet.doDelete(request, response);

        verify(columnService).deleteColumn(columnID);
        verify(response).sendRedirect("/boards/" + boardID);
    }


}
