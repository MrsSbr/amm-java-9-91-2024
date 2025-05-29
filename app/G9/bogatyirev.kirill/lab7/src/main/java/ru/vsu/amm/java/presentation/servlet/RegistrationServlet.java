package ru.vsu.amm.java.presentation.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.data.service.AuthService;
import ru.vsu.amm.java.domain.entities.Player;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private final AuthService authService;

    private static final Logger log = Logger.getLogger(RegistrationServlet.class.getName());


    public RegistrationServlet() {
        this.authService = new AuthService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/view/registration.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("password_confirm");

        if (password.equals(passwordConfirm)) {
            log.info("Пароли совпали");
            Player player = authService.registration(email, login, password);
            request.getSession().setAttribute("player", player);
            response.sendRedirect("/laba7/login");
        }
        else {
            log.info("Пароли не совпали");
            request.setAttribute("error", "Passwords do not match");
            request.getRequestDispatcher("/WEB-INF/view/registration.jsp").forward(request, response);
        }


    }
}