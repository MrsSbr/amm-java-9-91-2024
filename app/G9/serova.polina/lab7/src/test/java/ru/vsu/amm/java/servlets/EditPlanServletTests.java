package ru.vsu.amm.java.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import ru.vsu.amm.java.entity.PlanEntity;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.PlanService;
import ru.vsu.amm.java.service.UserService;
import ru.vsu.amm.java.util.TimeParser;
import ru.vsu.amm.java.util.UserNameFormatter;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EditPlanServletTests {

    private EditPlanServlet servlet;
    private PlanService planService;
    private UserService userService;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private HttpSession session;
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    void setUp() {
        planService = mock(PlanService.class);
        userService = mock(UserService.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        session = mock(HttpSession.class);
        requestDispatcher = mock(RequestDispatcher.class);

        try (MockedStatic<TimeParser> mockedTimeParser = mockStatic(TimeParser.class);
             MockedStatic<UserNameFormatter> mockedNameFormatter = mockStatic(UserNameFormatter.class)) {
            mockedTimeParser.when(() -> TimeParser.parseTimes((String[]) any()))
                    .thenReturn(Collections.emptyList());
            mockedNameFormatter.when(() -> UserNameFormatter.formatName(any()))
                    .thenReturn("Test Patient");

            servlet = new EditPlanServlet(planService, userService);
        }

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    void doGet_shouldShowEditFormForValidPlan() throws Exception {
        UserEntity currentUser = createTestUser(1L, "doctor@test.com");
        PlanEntity testPlan = createTestPlan(123L, 1L, 2L);
        UserEntity patient = createTestUser(2L, "patient@test.com");

        when(session.getAttribute("user")).thenReturn(currentUser);
        when(request.getParameter("id")).thenReturn("123");
        when(planService.getPlanById(123L)).thenReturn(testPlan);
        when(userService.getUserById(2L)).thenReturn(patient);

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("plan"), eq(testPlan));
        verify(request).setAttribute(eq("patientName"), anyString());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGet_shouldReturnNotFoundForMissingPlan() throws Exception {
        UserEntity currentUser = createTestUser(1L, "doctor@test.com");

        when(session.getAttribute("user")).thenReturn(currentUser);
        when(request.getParameter("id")).thenReturn("123");
        when(planService.getPlanById(123L)).thenReturn(null);

        servlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void doGet_shouldReturnForbiddenForWrongDoctor() throws Exception {
        UserEntity currentUser = createTestUser(1L, "doctor@test.com");
        PlanEntity testPlan = createTestPlan(123L, 999L, 2L); // Другой доктор

        when(session.getAttribute("user")).thenReturn(currentUser);
        when(request.getParameter("id")).thenReturn("123");
        when(planService.getPlanById(123L)).thenReturn(testPlan);

        servlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void doPost_shouldUpdatePlanAndRedirect() throws Exception {
        UserEntity currentUser = createTestUser(1L, "doctor@test.com");
        PlanEntity testPlan = createTestPlan(123L, 1L, 2L);

        when(session.getAttribute("user")).thenReturn(currentUser);
        when(request.getParameter("id")).thenReturn("123");
        when(planService.getPlanById(123L)).thenReturn(testPlan);
        when(request.getParameter("medicationName")).thenReturn("Aspirin");
        when(request.getParameter("dosageMg")).thenReturn("100.0");
        when(request.getParameter("durationDays")).thenReturn("7");
        when(request.getParameterValues("takingTime")).thenReturn(new String[]{"08:00"});

        servlet.doPost(request, response);

        verify(planService).updatePlan(
                eq(123L),
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
        UserEntity currentUser = createTestUser(1L, "doctor@test.com");
        PlanEntity testPlan = createTestPlan(123L, 1L, 2L);

        when(session.getAttribute("user")).thenReturn(currentUser);
        when(request.getParameter("id")).thenReturn("123");
        when(planService.getPlanById(123L)).thenReturn(testPlan);
        when(request.getParameter("medicationName")).thenReturn("Aspirin");
        when(request.getParameter("dosageMg")).thenReturn("invalid");

        servlet.doPost(request, response);

        verify(request).setAttribute(eq("error"), anyString());
    }

    private UserEntity createTestUser(Long id, String email) {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setEmail(email);
        return user;
    }

    private PlanEntity createTestPlan(Long id, Long doctorId, Long patientId) {
        PlanEntity plan = new PlanEntity();
        plan.setId(id);
        plan.setDoctorId(doctorId);
        plan.setPatientId(patientId);
        return plan;
    }
}