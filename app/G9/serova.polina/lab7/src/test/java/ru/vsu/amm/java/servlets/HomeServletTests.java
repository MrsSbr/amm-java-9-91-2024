package ru.vsu.amm.java.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.dto.PlanViewDto;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.PlanService;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class HomeServletTests {

    private HomeServlet servlet;
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

        servlet = new HomeServlet(planService);

        when(request.getSession(false)).thenReturn(session);
        when(request.getRequestDispatcher(any())).thenReturn(requestDispatcher);
    }

    @Test
    void doGet_shouldLoadDoctorPlansForDoctor() throws Exception {
        UserEntity doctor = createTestUser(Role.DOCTOR);
        List<PlanViewDto> doctorPlans = Collections.singletonList(createTestPlanDto());
        List<PlanViewDto> patientPlans = Collections.emptyList();

        when(session.getAttribute("user")).thenReturn(doctor);
        when(planService.getDoctorPlans(anyLong())).thenReturn(doctorPlans);
        when(planService.getPatientPlans(anyLong())).thenReturn(patientPlans);

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("doctorPlans"), eq(doctorPlans));
        verify(request).setAttribute(eq("patientPlans"), eq(patientPlans));
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGet_shouldNotLoadDoctorPlansForPatient() throws Exception {
        UserEntity patient = createTestUser(Role.PATIENT);
        List<PlanViewDto> patientPlans = Collections.singletonList(createTestPlanDto());

        when(session.getAttribute("user")).thenReturn(patient);
        when(planService.getPatientPlans(anyLong())).thenReturn(patientPlans);

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("patientPlans"), eq(patientPlans));
        verify(request, never()).setAttribute(eq("doctorPlans"), any());
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGet_shouldHandleError() throws Exception {
        UserEntity user = createTestUser(Role.DOCTOR);
        when(session.getAttribute("user")).thenReturn(user);
        when(planService.getDoctorPlans(anyLong())).thenThrow(new RuntimeException("Test error"));

        servlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    private UserEntity createTestUser(Role role) {
        UserEntity user = new UserEntity();
        user.setId(1L);
        user.setEmail("test@test.com");
        user.setRole(role);
        return user;
    }

    private PlanViewDto createTestPlanDto() {
        return new PlanViewDto(null, "Doctor", "Patient");
    }
}