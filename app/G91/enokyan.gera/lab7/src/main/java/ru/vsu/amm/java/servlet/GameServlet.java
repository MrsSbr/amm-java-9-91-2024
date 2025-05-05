package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.dto.GameViewExtendedDto;
import ru.vsu.amm.java.service.GameService;

import java.io.IOException;

@WebServlet("/game/*")
public class GameServlet extends HttpServlet {
    GameService gameService;

    public GameServlet() {
        gameService = new GameService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 1) {
            GameViewExtendedDto game = gameService.getGameById(Long.parseLong(pathInfo.substring(1)));

            if (game != null) {
                request.setAttribute("game", game);

                if (gameService.deleteGame(game.id())) {
                    request.getRequestDispatcher("/game.jsp?success=1").forward(request, response);
                } else {
                    request.getRequestDispatcher("/game.jsp?error=cantDeleteGame").forward(request, response);
                }

                return;
            }
        }

        response.sendRedirect(request.getRequestURI() + "?error=1");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 1) {
            GameViewExtendedDto game = gameService.getGameById(Long.parseLong(pathInfo.substring(1)));

            if (game != null) {
                request.setAttribute("game", game);
                request.getRequestDispatcher("/game.jsp").forward(request, response);
                return;
            }
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}