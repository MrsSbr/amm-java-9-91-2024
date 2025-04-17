package ru.vsu.amm.java.presentation.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import main.domain.entities.Player;
import main.domain.repository.PlayerRepository;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private final PlayerRepository playerRepository;

    public LoginServlet() {
        this.playerRepository = new PlayerRepository();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        try {
            Player player = playerRepository.findByLogin(login);

            if (player == null) {
                // Пользователь не найден
                req.setAttribute("error", "Пользователь с таким логином не найден");
                req.getRequestDispatcher("/WEB-INF/view/login.jsp").forward(req, resp);
                return;
            }

            if (playerRepository.getPassword(player).equals(password)) {
                HttpSession session = req.getSession();
                session.setAttribute("player", player);

            }
        } catch (SQLException e) {
            req.setAttribute("error", e.getMessage());
        }
    }
}
