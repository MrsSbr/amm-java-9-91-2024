package ru.vsu.amm.java.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.PlanService;

import java.io.IOException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class DeletePlanServletTests {

    private DeletePlanServlet servlet;
    private PlanService planService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    void setUp() {
        planService = mock(PlanService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        requestDispatcher = mock(RequestDispatcher.class);
        servlet = new DeletePlanServlet(planService);

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    void doPost_shouldDeletePlanAndRedirect() throws ServletException, IOException {
        UserEntity currentUser = createTestUser("doctor@test.com");
        when(session.getAttribute("user")).thenReturn(currentUser);
        when(request.getParameter("id")).thenReturn("123");

        servlet.doPost(request, response);

        verify(planService).deletePlanById(123L);
        verify(response).sendRedirect(anyString());
    }

    @Test
    void doPost_shouldHandleError() throws ServletException, IOException {
        UserEntity currentUser = createTestUser("doctor@test.com");
        when(session.getAttribute("user")).thenReturn(currentUser);
        when(request.getParameter("id")).thenReturn("123");
        doThrow(new RuntimeException("Test error")).when(planService).deletePlanById(123L);

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("error"), anyString());
        verify(requestDispatcher).forward(request, response);
    }

    private UserEntity createTestUser(String email) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        return user;
    }
}