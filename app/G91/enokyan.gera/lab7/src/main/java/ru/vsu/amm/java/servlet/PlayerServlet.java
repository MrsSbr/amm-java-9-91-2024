package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.dto.GameViewDto;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.GameService;
import ru.vsu.amm.java.service.UserService;

import java.io.IOException;
import java.util.List;

@WebServlet("/player/*")
public class PlayerServlet extends HttpServlet {
    UserService userService;
    GameService gameService;

    public PlayerServlet() {
        userService = new UserService();
        gameService = new GameService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 1) {
            UserEntity user = userService.getUserByNickname(pathInfo.substring(1));

            if (user != null) {
                List<GameViewDto> games = gameService.getLatestGamesByUserId(user.getId(), 10);
                request.setAttribute("player", user);
                request.setAttribute("games", games);
                request.getRequestDispatcher("/player.jsp").forward(request, response);
                return;
            }
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}