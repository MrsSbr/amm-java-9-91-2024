package ru.vsu.amm.java.servlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.UserEntity;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class LogoutServletTests {

    private LogoutServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        servlet = new LogoutServlet();

        when(request.getRemoteAddr()).thenReturn("192.168.1.1");
    }

    @Test
    void doGet_shouldLogoutAuthenticatedUser() throws IOException {
        UserEntity testUser = createTestUser(1L, "test@test.com");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(testUser);

        servlet.doGet(request, response);

        verify(session).invalidate();
        verify(response).sendRedirect(anyString());
    }

    @Test
    void doGet_shouldHandleAnonymousUser() throws IOException {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);

        servlet.doGet(request, response);

        verify(session).invalidate();
    }

    @Test
    void doGet_shouldHandleNoSession() throws IOException {
        when(request.getSession(false)).thenReturn(null);

        servlet.doGet(request, response);
    }

    private UserEntity createTestUser(Long id, String email) {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setEmail(email);
        return user;
    }
}