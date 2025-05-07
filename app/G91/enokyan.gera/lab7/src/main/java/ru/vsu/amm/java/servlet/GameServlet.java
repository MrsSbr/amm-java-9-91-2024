package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.dto.GameViewExtendedDto;
import ru.vsu.amm.java.service.GameService;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/game/*")
public class GameServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(GameServlet.class.getName());

    GameService gameService;

    public GameServlet() {
        gameService = new GameService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.log(Level.FINE, "Запрос аннулирования партии");
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 1) {
            long id = Long.parseLong(pathInfo.substring(1));
            logger.log(Level.FINE, MessageFormat.format("Определен id запрашиваемой партии: {0}", id));
            GameViewExtendedDto game = gameService.getGameById(Long.parseLong(pathInfo.substring(1)));

            if (game != null) {
                request.setAttribute("game", game);
                if (gameService.deleteGame(game.id())) {
                    logger.log(Level.FINE, MessageFormat.format("Успешный запрос на аннулирование партии с id={0}", id));
                    request.getRequestDispatcher("/game.jsp?success=1").forward(request, response);
                } else {
                    logger.log(Level.WARNING, MessageFormat.format(
                            "Неудачная попытка запроса на аннулирование партии с id={0}: её уже не аннулировать",
                            id
                    ));
                    request.getRequestDispatcher("/game.jsp?error=cantDeleteGame").forward(request, response);
                }
                return;
            } else {
                logger.log(Level.WARNING, MessageFormat.format(
                        "Неудачная попытка запроса аннулирования партии с id={0}: она не найден",
                        id
                ));
            }
        }

        response.sendRedirect(request.getRequestURI() + "?error=1");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.log(Level.FINE, "Запрос карточки партии");
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 1) {
            long id = Long.parseLong(pathInfo.substring(1));
            logger.log(Level.FINE, MessageFormat.format("Определен id запрашиваемой партии: {0}", id));
            GameViewExtendedDto game = gameService.getGameById(id);

            if (game != null) {
                request.setAttribute("game", game);
                logger.log(Level.FINE, MessageFormat.format("Успешная попытка запроса карточки партии с id={0}", id));
                request.getRequestDispatcher("/game.jsp").forward(request, response);
                return;
            } else {
                logger.log(Level.WARNING, MessageFormat.format(
                        "Неудачная попытка запроса карточки партии с id={0}: она не найдена",
                        id
                ));
            }
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}