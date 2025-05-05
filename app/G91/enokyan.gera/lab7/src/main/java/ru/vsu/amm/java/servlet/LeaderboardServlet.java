package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/leaderboard")
public class LeaderboardServlet extends HttpServlet {
    UserService userService;

    public LeaderboardServlet() {
        userService = new UserService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserEntity> topUsers = userService.getTopRatedUsers(10);
        request.setAttribute("topUsers", topUsers);
        request.setAttribute("activePage", "leaderboard");
        request.getRequestDispatcher("/leaderboard.jsp").forward(request, response);
    }
}