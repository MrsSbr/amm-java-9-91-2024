package ru.vsu.amm.java.presentation.servlet;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.data.service.CardService;
import ru.vsu.amm.java.domain.entities.Card;
import ru.vsu.amm.java.domain.entities.Player;
import ru.vsu.amm.java.domain.entities.WordToAction;


import java.io.IOException;

@WebServlet("/game")
public class GameServlet extends HttpServlet {
    private final CardService cardService;

    public GameServlet() {
        cardService = new CardService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);

        if (session == null || session.getAttribute("player") == null) {
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        req.getRequestDispatcher("/WEB-INF/view/game.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("player") == null) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        Player player = (Player) req.getSession().getAttribute("player");
        String action = req.getParameter("action");

        try {
            if ("generateCard".equals(action)) {
                Card card = cardService.generateCard(player.getId());
                sendCardResponse(resp, card);
            } else if ("rollDice".equals(action)) {
                int diceResult = cardService.rollDice();
                sendDiceResponse(resp, diceResult);
            }

        } catch (Exception e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void sendCardResponse(HttpServletResponse resp, Card card) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        JsonObject json = new JsonObject();
        json.addProperty("topic", card.getTopic().toString());
        json.addProperty("difficulty", card.getDifficulty().toString());

        JsonArray wordsArray = new JsonArray();
        for (WordToAction wta : card.getWordsToActions()) {
            JsonObject wordObj = new JsonObject();
            wordObj.addProperty("action", wta.getAction().getName());
            wordObj.addProperty("word", wta.getWord());
            wordObj.addProperty("points", wta.getAction().getPoints());
            wordsArray.add(wordObj);
        }
        json.add("words", wordsArray);

        resp.getWriter().write(json.toString());
    }

    private void sendDiceResponse(HttpServletResponse resp, int diceResult) throws IOException {
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");

        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Pragma", "no-cache");

        JsonObject json = new JsonObject();
        json.addProperty("diceResult", diceResult);
        resp.getWriter().write(json.toString());
    }
}
