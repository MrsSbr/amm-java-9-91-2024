package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.UserService;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/leaderboard")
public class LeaderboardServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LeaderboardServlet.class.getName());

    UserService userService;

    public LeaderboardServlet() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.FINE, "Запрос топ-10 пользователей по рейтингу");
        List<UserEntity> topUsers = userService.getTopRatedUsers(10);
        if (topUsers.isEmpty()) {
            logger.log(Level.WARNING, "Не найдено ни одного пользователя для составления топ-10");
        } else {
            logger.log(Level.FINE, MessageFormat.format("Запрос на получение топ-10 получил {0} пользователей", topUsers.size()));
        }
        request.setAttribute("topUsers", topUsers);
        request.setAttribute("activePage", "leaderboard");
        request.getRequestDispatcher("/leaderboard.jsp").forward(request, response);
    }
}