package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.exceptions.AuthException;
import ru.vsu.amm.java.mapper.UserMapper;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.AuthService;
import ru.vsu.amm.java.utils.Redirection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/registerUser")
public class RegisterUserServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try {

            User user = authService.register(request);

            HttpSession session = request.getSession();
            session.setAttribute("user", user);

            String redirect = Redirection.redirectBasedOnRole(user);
            response.sendRedirect(redirect);

        } catch (AuthException e) {

            response.sendRedirect(String.format("register.jsp?error=%s", e.getMessage()));

        }
    }
}
