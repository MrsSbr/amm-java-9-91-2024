package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.requests.Auth.RegisterRequest;
import ru.vsu.amm.java.services.AuthService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.naming.AuthenticationException;
import java.io.IOException;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final AuthService authService;

    public RegisterServlet(AuthService authService) {
        this.authService = authService;
    }

    @Override
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {
        try {
            RegisterRequest registerRequest = new RegisterRequest(
                    request.getParameter("email"),
                    request.getParameter("password"),
                    request.getParameter("lastName"),
                    request.getParameter("firstName"),
                    request.getParameter("patronymicName"),
                    request.getParameter("phoneNumber")
            );

            User user = authService.register(registerRequest);

            HttpSession httpSession = request.getSession();
            httpSession.setAttribute("user", user);
            response.sendRedirect("books.jsp");
        } catch (AuthenticationException e) {
            response.sendRedirect(String.format("%s", e.getMessage())); // TODO: связать с jsp
        }
    }
}
