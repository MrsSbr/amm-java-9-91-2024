package ru.vsu.amm.java.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.PlanService;
import ru.vsu.amm.java.service.UserService;
import ru.vsu.amm.java.util.TimeParser;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CreatePlanServletTests {

    private CreatePlanServlet servlet;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private PlanService planService;
    private UserService userService;
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    void setUp() {
        planService = mock(PlanService.class);
        userService = mock(UserService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        requestDispatcher = mock(RequestDispatcher.class);

        try (MockedStatic<TimeParser> mockedTimeParser = mockStatic(TimeParser.class)) {
            mockedTimeParser.when(() -> TimeParser.parseTimes((String[]) any()))
                    .thenReturn(Collections.emptyList());

            servlet = new CreatePlanServlet(planService, userService);
        }

        when(request.getSession(false)).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    void doGet_shouldSetPatientsAttribute() throws Exception {
        UserEntity doctor = createTestUser(1L, "doctor@test.com");
        List<UserEntity> patients = List.of(
                createTestUser(2L, "patient1@test.com"),
                createTestUser(3L, "patient2@test.com")
        );

        when(session.getAttribute("user")).thenReturn(doctor);
        when(userService.getAllUsers()).thenReturn(patients);

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("patients"), any(List.class));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doPost_shouldCreatePlanAndRedirect() throws Exception {
        UserEntity doctor = createTestUser(1L, "doctor@test.com");
        when(session.getAttribute("user")).thenReturn(doctor);
        when(request.getParameter("patientId")).thenReturn("2");
        when(request.getParameter("medicationName")).thenReturn("Aspirin");
        when(request.getParameter("dosageMg")).thenReturn("100.0");
        when(request.getParameter("durationDays")).thenReturn("7");
        when(request.getParameterValues("takingTime")).thenReturn(new String[]{"08:00"});

        servlet.doPost(request, response);

        verify(planService).addPlan(
                eq("Aspirin"),
                eq(100.0),
                any(),
                eq(7),
                eq(1L),
                eq(2L)
        );
        verify(response).sendRedirect(anyString());
    }

    @Test
    void doPost_shouldHandleError() throws Exception {
        UserEntity doctor = createTestUser(1L, "doctor@test.com");
        when(session.getAttribute("user")).thenReturn(doctor);
        when(request.getParameter("patientId")).thenReturn("invalid");

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("error"), anyString());
        verify(requestDispatcher).forward(request, response);
    }

    private UserEntity createTestUser(Long id, String email) {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setEmail(email);
        return user;
    }
}