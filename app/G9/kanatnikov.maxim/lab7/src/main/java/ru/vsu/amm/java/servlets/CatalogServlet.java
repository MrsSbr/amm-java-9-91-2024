package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.BoardGame;
import ru.vsu.amm.java.service.BoardGamesServiceImpl;
import ru.vsu.amm.java.service.interfaces.BoardGamesService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/catalog")
public class CatalogServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(CatalogServlet.class.getName());
    private final BoardGamesService boardGamesService;

    public CatalogServlet() {
        boardGamesService = new BoardGamesServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException, ServletException {
        HttpSession session = req.getSession();
        if (session.getAttribute("user") == null) {
            resp.sendRedirect("/login");
            return;
        }

        try {
            List<BoardGame> games = boardGamesService.getAllBoardGames();
            req.setAttribute("games", games);
            req.getRequestDispatcher("/catalog.jsp").forward(req, resp);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error fetching games");
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
