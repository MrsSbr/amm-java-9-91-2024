package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.exceptions.AuthException;
import ru.vsu.amm.java.service.AuthService;
import ru.vsu.amm.java.utils.Redirection;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/signIn")
public class SignInServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        HttpSession session = request.getSession();

        try {

            User user = authService.signIn(request);

            session.setAttribute("user", user);

            String redirect = Redirection.redirectBasedOnRole(user);

            response.sendRedirect(redirect);

        } catch (AuthException e) {

            response.sendRedirect(String.format("signIn.jsp?error=%s", e.getMessage()));

        }

    }
}
