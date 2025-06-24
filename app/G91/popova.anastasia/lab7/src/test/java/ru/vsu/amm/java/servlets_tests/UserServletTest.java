package ru.vsu.amm.java.servlets_tests;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.service.UserService;
import ru.vsu.amm.java.servlets.UserServlet;
import ru.vsu.amm.java.utils.Validator;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserServlet userServlet;

    private MockedStatic<Validator> mockedValidator;

    private User user;
    private UUID userID;
    private String name = "Test User";

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException {
        userID = UUID.randomUUID();
        user = new User();
        user.setUserID(userID);
        user.setName(name);

        Field userServiceField = UserServlet.class.getDeclaredField("userService");
        userServiceField.setAccessible(true);
        userServiceField.set(userServlet, userService);
        mockedValidator = mockStatic(Validator.class);
    }

    @AfterEach
    void tearDown() {
        mockedValidator.close();
    }

    @Test
    void testDoGetRootAuthenticated() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getRequestDispatcher("/WEB-INF/views/users/profile.jsp")).thenReturn(requestDispatcher);

        userServlet.doGet(request, response);

        verify(request).setAttribute("user", user);
        verify(request).getRequestDispatcher("/WEB-INF/views/users/profile.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoGetRootUnauthenticated() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);

        userServlet.doGet(request, response);

        verify(response).sendRedirect("/auth/login");
    }

    @Test
    void testDoGetUserByIdSuccess() throws ServletException, IOException {
        String pathInfo = "/" + userID;
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(userService.getByID(userID)).thenReturn(user);
        when(request.getRequestDispatcher("/WEB-INF/views/users/view.jsp")).thenReturn(requestDispatcher);

        userServlet.doGet(request, response);

        verify(userService).getByID(userID);
        verify(request).setAttribute("user", user);
        verify(request).getRequestDispatcher("/WEB-INF/views/users/view.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoGetUserByIdNotFound() throws IOException, ServletException {
        String pathInfo = "/" + userID;
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(userService.getByID(userID)).thenThrow(new IllegalArgumentException("Пользователь не найден"));

        userServlet.doGet(request, response);

        verify(userService).getByID(userID);
        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDoGetInvalidPath() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/invalid/path");

        userServlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDoPostRegisterRedirects() throws IOException {
        when(request.getPathInfo()).thenReturn("/register");

        userServlet.doPost(request, response);

        verify(response).sendRedirect("/auth/register");
    }

    @Test
    void testDoPostInvalidPath() throws IOException {
        when(request.getPathInfo()).thenReturn("/invalid/path");

        userServlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDoPutAuthenticatedUserSuccess() throws ServletException, IOException {
        String pathInfo = "/" + userID;
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("name")).thenReturn("Updated User");
        mockedValidator.when(() -> Validator.isValidName("Updated User")).thenReturn(true);
        doNothing().when(userService).updateUserName(userID, "Updated User");

        userServlet.doPut(request, response);

        verify(userService).updateUserName(userID, "Updated User");
        verify(session).setAttribute("user", user);
        verify(response).sendRedirect("/users");
        assertEquals("Updated User", user.getName());
    }

    @Test
    void testDoPutUnauthenticatedUser() throws ServletException, IOException {
        String pathInfo = "/" + userID;
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);

        userServlet.doPut(request, response);

        verify(userService, never()).updateUserName(any(), any());
        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN, "Недостаточно прав для выполнения операции");
    }

    @Test
    void testDoPutUnauthorizedUser() throws ServletException, IOException {
        String pathInfo = "/" + userID;
        UUID differentUserID = UUID.randomUUID();
        User differentUser = new User();
        differentUser.setUserID(differentUserID);
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(differentUser);

        userServlet.doPut(request, response);

        verify(userService, never()).updateUserName(any(), any());
        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN, "Недостаточно прав для выполнения операции");
    }

    @Test
    void testDoPutEmptyName() throws ServletException, IOException {
        String pathInfo = "/" + userID;
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("name")).thenReturn("");
        when(request.getRequestDispatcher("/WEB-INF/views/users/edit.jsp")).thenReturn(requestDispatcher);

        userServlet.doPut(request, response);

        verify(userService, never()).updateUserName(any(), any());
        verify(request).setAttribute("error", "Имя пользователя не может быть пустым");
        verify(request).getRequestDispatcher("/WEB-INF/views/users/edit.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPutInvalidName() throws ServletException, IOException {
        String pathInfo = "/" + userID;
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("name")).thenReturn("Invalid@Name");
        mockedValidator.when(() -> Validator.isValidName("Invalid@Name")).thenReturn(false);
        when(request.getRequestDispatcher("/WEB-INF/views/users/edit.jsp")).thenReturn(requestDispatcher);

        userServlet.doPut(request, response);

        verify(userService, never()).updateUserName(any(), any());
        verify(request).setAttribute("error", "Некорректное имя пользователя");
        verify(request).setAttribute("currentName", "Invalid@Name");
        verify(request).getRequestDispatcher("/WEB-INF/views/users/edit.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPutNonExistentUser() throws ServletException, IOException {
        String pathInfo = "/" + userID;
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("name")).thenReturn("Updated User");
        mockedValidator.when(() -> Validator.isValidName("Updated User")).thenReturn(true);
        doThrow(new IllegalArgumentException("Пользователь не найден")).when(userService).updateUserName(userID, "Updated User");
        when(request.getRequestDispatcher("/WEB-INF/views/users/edit.jsp")).thenReturn(requestDispatcher);

        userServlet.doPut(request, response);

        verify(userService).updateUserName(userID, "Updated User");
        verify(request).setAttribute("error", "Пользователь не найден");
        verify(request).setAttribute("currentName", "Updated User");
        verify(request).getRequestDispatcher("/WEB-INF/views/users/edit.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoDeleteAuthenticatedUserSuccess() throws ServletException, IOException {
        String pathInfo = "/" + userID;
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);
        doNothing().when(userService).deleteUser(userID);

        userServlet.doDelete(request, response);

        verify(userService).deleteUser(userID);
        verify(session).invalidate();
        verify(response).sendRedirect("/auth/login");
    }

    @Test
    void testDoDeleteUnauthenticatedUser() throws ServletException, IOException {
        String pathInfo = "/" + userID;
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);

        userServlet.doDelete(request, response);

        verify(userService, never()).deleteUser(any());
        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void testDoDeleteUnauthorizedUser() throws ServletException, IOException {
        String pathInfo = "/" + userID;
        UUID differentUserID = UUID.randomUUID();
        User differentUser = new User();
        differentUser.setUserID(differentUserID);
        when(request.getPathInfo()).thenReturn(pathInfo);
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(differentUser);

        userServlet.doDelete(request, response);

        verify(userService, never()).deleteUser(any());
        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void testDoDeleteInvalidPath() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/invalid/path");

        userServlet.doDelete(request, response);

        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDoPutInvalidPath() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/invalid/path");

        userServlet.doPut(request, response);

        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDoPutEmptyPath() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/");

        userServlet.doPut(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    @Test
    void testDoDeleteEmptyPath() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/");

        userServlet.doDelete(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST);
    }



}
