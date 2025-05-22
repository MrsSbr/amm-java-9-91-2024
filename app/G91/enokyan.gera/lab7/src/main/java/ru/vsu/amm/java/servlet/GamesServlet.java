package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.dto.GameViewDto;
import ru.vsu.amm.java.service.GameService;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/games")
public class GamesServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GamesServlet.class.getName());

    GameService gameService;

    public GamesServlet() {
        gameService = new GameService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.log(Level.FINE, "Запрос на получение последних 10 партий");
        List<GameViewDto> games = gameService.getLatestGames(10);
        if (games.isEmpty()) {
            logger.log(Level.WARNING, "Не найдено ни одной партии для отображения последних десяти");
        } else {
            logger.log(Level.FINE, MessageFormat.format(
                    "Запрос на получение последних 10 партий получил {0} партий",
                    games.size()
            ));
        }
        request.setAttribute("games", games);
        request.setAttribute("activePage", "games");
        request.getRequestDispatcher("/games.jsp").forward(request, response);
    }
}