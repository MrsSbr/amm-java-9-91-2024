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
import java.util.List;

@WebServlet("/protected/referee/add")
public class AddGameServlet extends HttpServlet {
    private final UserService userService;
    private final GameService gameService;

    public AddGameServlet() {
        userService = new UserService();
        gameService = new GameService();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        long firstPlayersId = Long.parseLong(request.getParameter("player1"));
        long secondPlayersId = Long.parseLong(request.getParameter("player2"));

        if (firstPlayersId == secondPlayersId) {
            response.sendRedirect("/protected/referee/add?error=samePlayer");
        } else {
            GameResult result = GameResult.valueOf(request.getParameter("result"));
            gameService.addGame(firstPlayersId, secondPlayersId, result);
            response.sendRedirect("/protected/referee/add?success=1");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<UserEntity> players = userService.getAllUsers();
        request.setAttribute("players", players);
        request.setAttribute("activePage", "add");
        request.getRequestDispatcher("/protected/referee/add.jsp").forward(request, response);
    }
}
