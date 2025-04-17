package ru.vsu.amm.java.presentation.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import main.data.service.AuthService;
import main.domain.entities.Player;
import main.domain.repository.PlayerRepository;

import java.io.IOException;

@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    private final PlayerRepository playerRepository;
    private final AuthService authService;

    public RegistrationServlet() {
        this.playerRepository = new PlayerRepository();
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
//                Player player = new Player(login, password, email);
//            try {
//                playerRepository.create(player);
//            } catch (SQLException e) {
//                throw new RuntimeException(e);
//            }

            Player player = authService.registration(email, login, password);
            request.getSession().setAttribute("player", player);
            response.sendRedirect("/login");
        }
        else {
            request.setAttribute("error", "Passwords do not match");
            request.getRequestDispatcher("/WEB-INF/view/registration.jsp").forward(request, response);
        }


    }
}
