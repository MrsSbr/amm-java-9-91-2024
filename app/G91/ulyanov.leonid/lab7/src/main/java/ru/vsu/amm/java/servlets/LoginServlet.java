package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.requests.Auth.LoginRequest;
import ru.vsu.amm.java.services.AuthService;

import javax.naming.AuthenticationException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final AuthService authService;

    public LoginServlet(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        try {
            LoginRequest signInRequest = new LoginRequest(
                    request.getParameter("login"),
                    request.getParameter("password")
            );

            User user = authService.login(signInRequest);

            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("user", user);
            response.sendRedirect("books.jsp");
        } catch (AuthenticationException e) {
            response.sendRedirect(String.format("login.jsp?error=%s", e.getMessage()));
        }
    }
}
