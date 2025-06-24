package ru.vsu.amm.java.servlets_tests;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.service.UserService;
import ru.vsu.amm.java.servlets.AuthServlet;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.UUID;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServletTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private UserService userService;

    @InjectMocks
    private AuthServlet authServlet;

    private User user;
    private UUID userID;
    private String email = "test@example.com";
    private String password = "password123";
    private String name = "Test User";

    @BeforeEach
    void setUp() throws IllegalAccessException, NoSuchFieldException {
        userID = UUID.randomUUID();
        user = new User();
        user.setUserID(userID);
        user.setEmail(email);
        user.setName(name);
        user.setPassword("hashedPassword");

        //when(request.getSession()).thenReturn(session);
        //when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        Field userServiceField = AuthServlet.class.getDeclaredField("userService");
        userServiceField.setAccessible(true);
        userServiceField.set(authServlet, userService);
    }

    @Test
    void testDoGetRootRedirectsToLogin() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/");

        authServlet.doGet(request, response);

        verify(response).sendRedirect("/auth/login");
    }

    @Test
    void testDoGetLoginForwardsToLoginJsp() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/login");
        when(request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp")).thenReturn(requestDispatcher);

        authServlet.doGet(request, response);

        verify(request).getRequestDispatcher("/WEB-INF/views/auth/login.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoGetRegisterForwardsToRegisterJsp() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/register");
        when(request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp")).thenReturn(requestDispatcher);

        authServlet.doGet(request, response);

        verify(request).getRequestDispatcher("/WEB-INF/views/auth/register.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoGetInvalidPathReturnsNotFound() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/invalid");

        authServlet.doGet(request, response);

        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Test
    void testDoPostLoginSuccess() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/login");
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        when(userService.login(email, password)).thenReturn(user);
        when(request.getSession()).thenReturn(session);

        authServlet.doPost(request, response);

        verify(userService).login(email, password);
        verify(session).setAttribute("user", user);
        verify(response).sendRedirect("/boards");
    }

    @Test
    void testDoPostLoginFailure() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/login");
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        when(userService.login(email, password)).thenThrow(new SecurityException("Неверный пароль!"));
        when(request.getRequestDispatcher("/WEB-INF/views/auth/login.jsp")).thenReturn(requestDispatcher);

        authServlet.doPost(request, response);

        verify(userService).login(email, password);
        verify(request).setAttribute("error", "Неверный пароль!");
        verify(request).getRequestDispatcher("/WEB-INF/views/auth/login.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPostRegisterSuccess() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/register");
        when(request.getParameter("name")).thenReturn(name);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        when(userService.register(name, email, password))
                .thenThrow(new IllegalArgumentException("Некорректный email!"));
        when(request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp")).thenReturn(requestDispatcher);

        authServlet.doPost(request, response);

        verify(userService).register(name, email, password);
        verify(request).setAttribute("error", "Некорректный email!");
        verify(request).getRequestDispatcher("/WEB-INF/views/auth/register.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPostRegisterMissingFields() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/register");
        when(request.getParameter("name")).thenReturn("");
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        when(request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp")).thenReturn(requestDispatcher);

        authServlet.doPost(request, response);

        verify(userService, never()).register(anyString(), anyString(), anyString());
        verify(request).setAttribute("error", "Все поля обязательны для заполнения!");
        verify(request).getRequestDispatcher("/WEB-INF/views/auth/register.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPostRegisterFailure() throws ServletException, IOException {
        when(request.getPathInfo()).thenReturn("/register");
        when(request.getParameter("name")).thenReturn(name);
        when(request.getParameter("email")).thenReturn(email);
        when(request.getParameter("password")).thenReturn(password);
        when(userService.register(name, email, password)).thenThrow(new IllegalArgumentException("Некорректный email!"));
        when(request.getRequestDispatcher("/WEB-INF/views/auth/register.jsp")).thenReturn(requestDispatcher);

        authServlet.doPost(request, response);

        verify(userService).register(name, email, password);
        verify(request).setAttribute("error", "Некорректный email!");
        verify(request).getRequestDispatcher("/WEB-INF/views/auth/register.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testDoPostLogout() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/logout");
        when(request.getSession()).thenReturn(session);

        authServlet.doPost(request, response);

        verify(session).invalidate();
        verify(response).sendRedirect("/auth/login");
    }

    @Test
    void testDoPostInvalidPathReturnsNotFound() throws IOException, ServletException {
        when(request.getPathInfo()).thenReturn("/invalid");

        authServlet.doPost(request, response);

        verify(response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }


}
