package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.enums.Role;
import ru.vsu.amm.java.exceptions.AuthException;
import ru.vsu.amm.java.requests.RegisterRequest;
import ru.vsu.amm.java.service.AuthService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static ru.vsu.amm.java.utils.Redirection.redirectToActionsList;

@WebServlet("/registerUser")
public class RegisterUserServlet extends HttpServlet {

    private final AuthService authService;

    public RegisterUserServlet() {

        authService = new AuthService();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            RegisterRequest registerRequest = new RegisterRequest(
                    request.getParameter("lastName"),
                    request.getParameter("firstName"),
                    request.getParameter("patronymic"),
                    request.getParameter("login"),
                    request.getParameter("password"),
                    Role.USER
            );

            User user = authService.register(registerRequest);

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            String redirect = redirectToActionsList(user);
            response.sendRedirect(redirect);

        } catch (AuthException e) {

            response.sendRedirect(String.format("register.jsp?error=%s", e.getMessage()));

        }
    }
}
