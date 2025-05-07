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
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/player/*")
public class PlayerServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(PlayerServlet.class.getName());

    UserService userService;
    GameService gameService;

    public PlayerServlet() {
        userService = new UserService();
        gameService = new GameService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.log(Level.FINE, "Запрос карточки пользователя");
        String pathInfo = request.getPathInfo();

        if (pathInfo != null && pathInfo.length() > 1) {
            String nickname = pathInfo.substring(1);
            logger.log(Level.FINE, MessageFormat.format("Определен nickname запрашиваемого пользователя: {0}", nickname));

            UserEntity user = userService.getUserByNickname(pathInfo.substring(1));

            if (user != null) {
                List<GameViewDto> games = gameService.getLatestGamesByUserId(user.getId(), 10);
                request.setAttribute("player", user);
                request.setAttribute("games", games);
                logger.log(Level.FINE, MessageFormat.format(
                        "Успешная попытка запроса карточки пользователя с nickname={0}, id={1}",
                        nickname,
                        user.getId()
                ));
                request.getRequestDispatcher("/player.jsp").forward(request, response);
                return;
            } else {
                logger.log(Level.WARNING, MessageFormat.format(
                        "Неудачная попытка запроса карточки пользователя с nickname={0}: он не найден",
                        nickname
                ));
            }
        }

        response.sendError(HttpServletResponse.SC_NOT_FOUND);
    }
}