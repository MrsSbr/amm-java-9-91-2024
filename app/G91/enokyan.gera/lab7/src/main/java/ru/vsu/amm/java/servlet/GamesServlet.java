package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.dto.GameViewDto;
import ru.vsu.amm.java.service.GameService;

import java.io.IOException;
import java.util.List;

@WebServlet("/games")
public class GamesServlet extends HttpServlet {
    GameService gameService;

    public GamesServlet() {
        gameService = new GameService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<GameViewDto> games = gameService.getLatestGames(10);
        request.setAttribute("games", games);
        request.setAttribute("activePage", "games");
        request.getRequestDispatcher("/games.jsp").forward(request, response);
    }
}