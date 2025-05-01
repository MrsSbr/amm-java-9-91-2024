package ru.vsu.amm.java.servlets;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.UserService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PatientsServletTests {

    private PatientsServlet servlet;
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

        servlet = new PatientsServlet(userService);

        when(request.getSession(false)).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getRemoteAddr()).thenReturn("192.168.1.1");
    }

    @Test
    void doGet_shouldShowPatientsListForDoctor() throws Exception {
        UserEntity doctor = createTestUser(1L, "doctor@test.com", Role.DOCTOR);
        List<UserEntity> patients = Arrays.asList(
                createTestUser(2L, "patient1@test.com", Role.PATIENT),
                createTestUser(3L, "patient2@test.com", Role.PATIENT)
        );

        when(session.getAttribute("user")).thenReturn(doctor);
        when(userService.getAllUsers()).thenReturn(patients);

        servlet.doGet(request, response);

        verify(request).setAttribute("patients", patients);
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doGet_shouldRejectNonDoctorAccess() throws Exception {
        UserEntity patient = createTestUser(1L, "patient@test.com", Role.PATIENT);

        when(session.getAttribute("user")).thenReturn(patient);

        servlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN);
    }

    @Test
    void doGet_shouldHandleServiceError() throws Exception {
        UserEntity doctor = createTestUser(1L, "doctor@test.com", Role.DOCTOR);

        when(session.getAttribute("user")).thenReturn(doctor);
        when(userService.getAllUsers()).thenThrow(new RuntimeException("DB error"));

        servlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    }

    @Test
    void doGet_shouldExcludeCurrentDoctorFromList() throws Exception {
        UserEntity doctor = createTestUser(1L, "doctor@test.com", Role.DOCTOR);
        List<UserEntity> users = Arrays.asList(
                doctor,
                createTestUser(2L, "patient1@test.com", Role.PATIENT)
        );

        when(session.getAttribute("user")).thenReturn(doctor);
        when(userService.getAllUsers()).thenReturn(users);

        servlet.doGet(request, response);

        verify(request).setAttribute(eq("patients"), any(List.class));
        verify(request.getRequestDispatcher("/protected/doctor/patients.jsp")).forward(request, response);
    }

    private UserEntity createTestUser(Long id, String email, Role role) {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setEmail(email);
        user.setRole(role);
        return user;
    }
}