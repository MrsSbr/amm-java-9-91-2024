package ru.vsu.amm.java.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.anyString;


class AuthFilterTests {

    private AuthFilter authFilter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;
    private HttpSession session;

    @BeforeEach
    void setUp() {
        authFilter = new AuthFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        session = mock(HttpSession.class);
    }

    @Test
    void doFilter_shouldRedirectWhenNoSession() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(null);
        when(request.getRequestURI()).thenReturn("/protected/resource");
        when(request.getRemoteAddr()).thenReturn("192.168.1.1");
        when(request.getContextPath()).thenReturn("/app");

        authFilter.doFilter(request, response, chain);

        verify(response).sendRedirect("/app/login");
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    void doFilter_shouldRedirectWhenNoUserInSession() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn(null);
        when(request.getRequestURI()).thenReturn("/protected/resource");
        when(request.getRemoteAddr()).thenReturn("192.168.1.1");
        when(request.getContextPath()).thenReturn("/app");

        authFilter.doFilter(request, response, chain);

        verify(response).sendRedirect("/app/login");
        verify(chain, never()).doFilter(request, response);
    }

    @Test
    void doFilter_shouldAllowAccessWhenUserAuthenticated() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn("testUser");
        when(session.getAttribute("role")).thenReturn("ADMIN");
        when(request.getRequestURI()).thenReturn("/protected/resource");

        authFilter.doFilter(request, response, chain);

        verify(response, never()).sendRedirect(anyString());
        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilter_shouldHandleServletException() throws ServletException, IOException {
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("user")).thenReturn("testUser");
        when(request.getRequestURI()).thenReturn("/protected/resource");
        doThrow(new ServletException("Test error")).when(chain).doFilter(request, response);

        assertThrows(ServletException.class, () ->
                authFilter.doFilter(request, response, chain)
        );
    }

    @Test
    void doFilter_shouldHandleIOException() throws IOException {
        when(request.getSession(false)).thenReturn(null);
        when(request.getRequestURI()).thenReturn("/protected/resource");
        when(request.getContextPath()).thenReturn("/app");
        doThrow(new IOException("Redirect failed")).when(response).sendRedirect("/app/login");

        assertThrows(IOException.class, () ->
                authFilter.doFilter(request, response, chain)
        );
    }
}