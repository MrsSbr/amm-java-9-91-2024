package ru.vsu.amm.java.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.mindrot.jbcrypt.BCrypt;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.UserService;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RegisterServletTests {

    private RegisterServlet servlet;
    private UserService userService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        requestDispatcher = mock(RequestDispatcher.class);

        try (MockedStatic<BCrypt> mockedBCrypt = mockStatic(BCrypt.class)) {

            mockedBCrypt.when(() -> BCrypt.hashpw(anyString(), anyString()))
                    .thenReturn("hashedPassword");
            mockedBCrypt.when(BCrypt::gensalt)
                    .thenReturn("salt");

            servlet = new RegisterServlet(userService);
        }

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getRemoteAddr()).thenReturn("192.168.1.1");
    }

    @Test
    void doGet_shouldShowRegistrationPage() throws Exception {
        servlet.doGet(request, response);

        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPost_shouldRegisterNewUser() throws Exception {
        when(request.getParameter("email")).thenReturn("new@test.com");
        when(request.getParameter("name")).thenReturn("Polina");
        when(request.getParameter("surname")).thenReturn("Serova");
        when(request.getParameter("patronymic")).thenReturn("Alekseevna");
        when(request.getParameter("birthday")).thenReturn("2000-01-01");
        when(request.getParameter("password")).thenReturn("test");
        when(request.getParameter("role")).thenReturn("PATIENT");
        when(userService.getUserByEmail("new@test.com")).thenReturn(null);

        servlet.doPost(request, response);

        verify(userService).addUser(
                eq("Polina"),
                eq("Serova"),
                eq("Alekseevna"),
                eq(LocalDate.of(2000, 1, 1)),
                eq("new@test.com"),
                startsWith("$2a$10$"),
                eq(Role.PATIENT)
        );
        verify(response).sendRedirect("/login.jsp?success=1");
    }

    @Test
    void doPost_shouldHandleExistingEmail() throws Exception {
        UserEntity existingUser = new UserEntity();
        when(request.getParameter("email")).thenReturn("new@test.com");
        when(request.getParameter("name")).thenReturn("Polina");
        when(request.getParameter("surname")).thenReturn("Serova");
        when(request.getParameter("patronymic")).thenReturn("Alekseevna");
        when(request.getParameter("birthday")).thenReturn("2000-01-01");
        when(request.getParameter("password")).thenReturn("test");
        when(request.getParameter("role")).thenReturn("PATIENT");
        when(userService.getUserByEmail("new@test.com")).thenReturn(existingUser);

        servlet.doPost(request, response);

        verify(response).sendRedirect("/register.jsp?error=email_exists");
    }

    @Test
    void doPost_shouldHandleSystemError() throws Exception {
        when(request.getParameter("email")).thenReturn("test@test.com");
        when(userService.getUserByEmail("test@test.com")).thenThrow(new RuntimeException("DB error"));

        servlet.doPost(request, response);

        verify(response).sendRedirect("/register.jsp?error=system_error");
    }
}