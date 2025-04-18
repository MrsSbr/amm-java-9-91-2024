package ru.vsu.amm.java.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.vsu.amm.java.entity.Role;
import ru.vsu.amm.java.entity.UserEntity;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doThrow;

class RoleFilterTests {

    private RoleFilter roleFilter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;
    private HttpSession session;
    private UserEntity doctorUser;
    private UserEntity patientUser;

    @BeforeEach
    void setUp() {
        roleFilter = new RoleFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        session = mock(HttpSession.class);

        doctorUser = new UserEntity();
        doctorUser.setRole(Role.DOCTOR);

        patientUser = new UserEntity();
        patientUser.setRole(Role.PATIENT);
    }

    @Test
    void doFilter_shouldDenyAccessWhenNoSession() throws Exception {
        when(request.getSession(false)).thenReturn(null);
        when(request.getRequestURI()).thenReturn("/protected/doctor/patients");
        when(request.getRemoteAddr()).thenReturn("192.168.1.1");

        roleFilter.doFilter(request, response, chain);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    void doFilter_shouldDenyAccessWhenNoUserInSession() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getRequestURI()).thenReturn("/protected/doctor/patients");

        roleFilter.doFilter(request, response, chain);

        verify(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    void doFilter_shouldDenyAccessWhenUserNotDoctor() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(patientUser);
        when(request.getRequestURI()).thenReturn("/protected/doctor/patients");

        roleFilter.doFilter(request, response, chain);

        verify(response).sendError(HttpServletResponse.SC_FORBIDDEN);
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    void doFilter_shouldAllowAccessWhenUserIsDoctor() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(doctorUser);
        when(request.getRequestURI()).thenReturn("/protected/doctor/patients");

        roleFilter.doFilter(request, response, chain);

        verify(response, never()).sendError(anyInt());
        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilter_shouldHandleServletException() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(doctorUser);
        when(request.getRequestURI()).thenReturn("/protected/doctor/patients");
        doThrow(new ServletException("Test error")).when(chain).doFilter(request, response);

        assertThrows(ServletException.class, () ->
                roleFilter.doFilter(request, response, chain)
        );
    }

    @Test
    void doFilter_shouldHandleIOException() throws Exception {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(doctorUser);
        when(request.getRequestURI()).thenReturn("/protected/doctor/patients");
        doThrow(new IOException("Test error")).when(chain).doFilter(request, response);

        assertThrows(IOException.class, () ->
                roleFilter.doFilter(request, response, chain)
        );
    }
}
