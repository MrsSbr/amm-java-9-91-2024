package ru.vsu.amm.java.servlets;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.UserEntity;

import java.io.IOException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RootRedirectServletTests {

    private RootRedirectServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);

        servlet = new RootRedirectServlet();

        when(request.getRemoteAddr()).thenReturn("192.168.1.1");
        when(request.getContextPath()).thenReturn("/context");
    }

    @Test
    void doGet_shouldRedirectAuthenticatedUserToHome() throws IOException {
        UserEntity user = createTestUser(1L, "user@test.com");
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(user);

        servlet.doGet(request, response);

        verify(response).sendRedirect("/context/protected/home");
    }

    @Test
    void doGet_shouldRedirectUnauthenticatedUserToLogin() throws IOException {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);

        servlet.doGet(request, response);

        verify(response).sendRedirect("/context/login");
    }

    @Test
    void doGet_shouldRedirectWhenNoSession() throws IOException {
        when(request.getSession(false)).thenReturn(null);

        servlet.doGet(request, response);

        verify(response).sendRedirect("/context/login");
    }

    private UserEntity createTestUser(Long id, String email) {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setEmail(email);
        return user;
    }
}