package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.requests.Auth.LoginRequest;
import ru.vsu.amm.java.services.AuthService;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final AuthService authService;

    public LoginServlet() {
        this.authService = new AuthService();
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        try {
            LoginRequest loginRequest = new LoginRequest(
                    request.getParameter("email"),
                    request.getParameter("password")
            );

            User user = authService.login(loginRequest);

            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("user", user);
            response.sendRedirect("books.jsp");
        } catch (AuthenticationException e) {
            response.sendRedirect(String.format("%s", e.getMessage())); // TODO: связать с jsp
        }
    }

    @Override
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
}
