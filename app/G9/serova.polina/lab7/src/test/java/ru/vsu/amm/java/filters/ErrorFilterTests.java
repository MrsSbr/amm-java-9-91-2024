package ru.vsu.amm.java.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ErrorFilterTests {

    private ErrorFilter errorFilter;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain chain;
    private RequestDispatcher requestDispatcher;

    @BeforeEach
    void setUp() {
        errorFilter = new ErrorFilter();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
        chain = mock(FilterChain.class);
        requestDispatcher = mock(RequestDispatcher.class);
    }

    @Test
    void doFilter_shouldProcessRequestNormally() throws ServletException, IOException {
        errorFilter.doFilter(request, response, chain);

        verify(chain).doFilter(request, response);
    }

    @Test
    void doFilter_shouldHandleServletException() throws ServletException, IOException {
        String testUri = "/test/uri";
        ServletException testException = new ServletException("Test error");

        when(request.getRequestURI()).thenReturn(testUri);
        when(request.getRequestDispatcher("/")).thenReturn(requestDispatcher);
        doThrow(testException).when(chain).doFilter(request, response);

        errorFilter.doFilter(request, response, chain);

        verify(request).setAttribute("errorMessage", "Произошла ошибка при обработке запроса");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doFilter_shouldHandleIOException() throws ServletException, IOException {
        String testUri = "/test/uri";
        IOException testException = new IOException("IO error");

        when(request.getRequestURI()).thenReturn(testUri);
        when(request.getRequestDispatcher("/")).thenReturn(requestDispatcher);
        doThrow(testException).when(chain).doFilter(request, response);

        errorFilter.doFilter(request, response, chain);

        verify(request).setAttribute("errorMessage", "Произошла ошибка при обработке запроса");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doFilter_shouldHandleOtherExceptions() throws ServletException, IOException {
        String testUri = "/test/uri";
        RuntimeException testException = new RuntimeException("Unexpected error");

        when(request.getRequestURI()).thenReturn(testUri);
        when(request.getRequestDispatcher("/")).thenReturn(requestDispatcher);
        doThrow(testException).when(chain).doFilter(request, response);

        errorFilter.doFilter(request, response, chain);

        verify(request).setAttribute("errorMessage", "Произошла ошибка при обработке запроса");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void doFilter_shouldWorkWithNonHttpRequest() throws ServletException, IOException {
        ServletRequest nonHttpRequest = mock(ServletRequest.class);
        ServletResponse nonHttpResponse = mock(ServletResponse.class);

        doThrow(new ServletException("Test error")).when(chain).doFilter(nonHttpRequest, nonHttpResponse);
        when(nonHttpRequest.getRequestDispatcher("/")).thenReturn(requestDispatcher);

        errorFilter.doFilter(nonHttpRequest, nonHttpResponse, chain);

        verify(nonHttpRequest).setAttribute("errorMessage", "Произошла ошибка при обработке запроса");
        verify(requestDispatcher).forward(nonHttpRequest, nonHttpResponse);
    }
}