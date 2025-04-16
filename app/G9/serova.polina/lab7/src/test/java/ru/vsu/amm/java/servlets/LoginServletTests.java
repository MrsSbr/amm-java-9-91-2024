package ru.vsu.amm.java.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.UserService;


import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LoginServletTests {

    private LoginServlet servlet;
    private UserService userService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        requestDispatcher = mock(RequestDispatcher.class);

        try (MockedStatic<BCrypt> mockedBCrypt = mockStatic(BCrypt.class)) {
            mockedBCrypt.when(() -> BCrypt.checkpw(anyString(), anyString()))
                    .thenReturn(false);

            servlet = new LoginServlet(userService);
        }

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getRemoteAddr()).thenReturn("192.168.1.1");
    }

    @Test
    void doGet_shouldShowLoginPage() throws Exception {
        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPost_shouldLoginSuccessfully() throws Exception {
        UserEntity testUser = createTestUser(1L, "test@test.com", "$2a$12$xX.tK48BAjxsgZkzyQyFl.9ggBujYALbNTJyiM40u4w.cOFq6vVBq");

        when(userService.getUserByEmail("test@test.com")).thenReturn(testUser);
        when(request.getParameter("email")).thenReturn("test@test.com");
        when(request.getParameter("password")).thenReturn("test");

        servlet.doPost(request, response);

        verify(session).setAttribute("user", testUser);
        verify(response).sendRedirect("protected/home");
    }

    @Test
    void doPost_shouldHandleInvalidCredentials() throws Exception {
        when(userService.getUserByEmail("test@test.com")).thenReturn(null);
        when(request.getParameter("email")).thenReturn("test@test.com");
        when(request.getParameter("password")).thenReturn("wrong");

        servlet.doPost(request, response);

        verify(response).sendRedirect("login.jsp?error=1");
    }

    @Test
    void doPost_shouldHandleException() throws Exception {
        when(userService.getUserByEmail("test@test.com")).thenThrow(new RuntimeException("DB error"));
        when(request.getParameter("email")).thenReturn("test@test.com");
        when(request.getParameter("password")).thenReturn("password");

        servlet.doPost(request, response);

        verify(response).sendRedirect("login.jsp?error=2");
    }

    private UserEntity createTestUser(Long id, String email, String passwordHash) {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setEmail(email);
        user.setPasswordHash(passwordHash);
        return user;
    }
}