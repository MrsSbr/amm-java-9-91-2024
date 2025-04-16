package ru.vsu.amm.java.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import ru.vsu.amm.java.entity.PlanEntity;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.PlanService;
import ru.vsu.amm.java.service.UserService;
import ru.vsu.amm.java.util.UserNameFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ViewPlanServletTests {

    private ViewPlanServlet servlet;
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

        try (MockedStatic<UserNameFormatter> mockedNameFormatter = mockStatic(UserNameFormatter.class)) {

            mockedNameFormatter.when(() -> UserNameFormatter.formatName(any()))
                    .thenReturn("Test Name");

            servlet = new ViewPlanServlet(planService, userService);
        }

        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getRemoteAddr()).thenReturn("192.168.1.1");
    }

    @Test
    void doGet_shouldShowPlanForDoctor() throws Exception {
        UserEntity doctor = createTestUser(1L, "doctor@test.com", Role.DOCTOR);
        PlanEntity plan = createTestPlan(123L, 1L, 2L);
        UserEntity patient = createTestUser(2L, "patient@test.com", Role.PATIENT);

        when(session.getAttribute("user")).thenReturn(doctor);
        when(request.getParameter("id")).thenReturn("123");
        when(planService.getPlanById(123L)).thenReturn(plan);
        when(userService.getUserById(1L)).thenReturn(doctor);
        when(userService.getUserById(2L)).thenReturn(patient);

        servlet.doGet(request, response);

        verify(request).setAttribute("plan", plan);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGet_shouldShowPlanForPatient() throws Exception {
        UserEntity patient = createTestUser(2L, "patient@test.com", Role.PATIENT);
        PlanEntity plan = createTestPlan(123L, 1L, 2L);
        UserEntity doctor = createTestUser(1L, "doctor@test.com", Role.DOCTOR);

        when(session.getAttribute("user")).thenReturn(patient);
        when(request.getParameter("id")).thenReturn("123");
        when(planService.getPlanById(123L)).thenReturn(plan);
        when(userService.getUserById(1L)).thenReturn(doctor);
        when(userService.getUserById(2L)).thenReturn(patient);

        servlet.doGet(request, response);

        verify(request).setAttribute("plan", plan);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGet_shouldReturnNotFoundForMissingPlan() throws Exception {
        UserEntity user = createTestUser(1L, "user@test.com", Role.DOCTOR);

        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("id")).thenReturn("123");
        when(planService.getPlanById(123L)).thenReturn(null);

        servlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void doGet_shouldReturnForbiddenForUnauthorizedAccess() throws Exception {
        UserEntity user = createTestUser(3L, "other@test.com", Role.PATIENT);
        PlanEntity plan = createTestPlan(123L, 1L, 2L); // План принадлежит другому пациенту

        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("id")).thenReturn("123");
        when(planService.getPlanById(123L)).thenReturn(plan);

        servlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void doGet_shouldHandleInvalidPlanId() throws Exception {
        UserEntity user = createTestUser(1L, "user@test.com", Role.DOCTOR);

        when(session.getAttribute("user")).thenReturn(user);
        when(request.getParameter("id")).thenReturn("invalid");

        servlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_BAD_REQUEST);
    }

    private UserEntity createTestUser(Long id, String email, Role role) {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setEmail(email);
        user.setRole(role);
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