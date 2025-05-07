package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.entity.GameResult;
import ru.vsu.amm.java.entity.UserEntity;
import ru.vsu.amm.java.service.GameService;
import ru.vsu.amm.java.service.UserService;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/protected/referee/add")
public class AddGameServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AddGameServlet.class.getName());

    private final UserService userService;
    private final GameService gameService;

    public AddGameServlet() {
        userService = new UserService();
        gameService = new GameService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.FINE, "Запрос на добавление партии");
        long firstPlayersId = Long.parseLong(request.getParameter("player1"));
        long secondPlayersId = Long.parseLong(request.getParameter("player2"));

        if (firstPlayersId == secondPlayersId) {
            logger.log(Level.WARNING, MessageFormat.format(
                    "Неудачная попытка добавления партии: выбран один и тот же пользователь с id={0}",
                    firstPlayersId
            ));
            response.sendRedirect("/protected/referee/add?error=samePlayer");
        } else {
            GameResult result = GameResult.valueOf(request.getParameter("result"));
            gameService.addGame(firstPlayersId, secondPlayersId, result);
            logger.log(Level.WARNING, MessageFormat.format(
                    "Успешная попытка добавления партии User[id={0}]–User[id={1}]: {2}",
                    firstPlayersId,
                    secondPlayersId,
                    result
            ));
            response.sendRedirect("/protected/referee/add?success=1");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.log(Level.FINE, "Запрос страницы добавления партии");
        List<UserEntity> players = userService.getAllUsers();
        request.setAttribute("players", players);
        request.setAttribute("activePage", "add");
        request.getRequestDispatcher("/protected/referee/add.jsp").forward(request, response);
    }
}
