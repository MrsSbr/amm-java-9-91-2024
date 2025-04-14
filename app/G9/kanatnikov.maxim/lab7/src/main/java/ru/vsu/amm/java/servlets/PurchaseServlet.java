package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.requests.CreatePurchaseHistoryRequest;
import ru.vsu.amm.java.service.PurchaseHistoryServiceImpl;
import ru.vsu.amm.java.service.interfaces.PurchaseHistoryService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/purchase")
public class PurchaseServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(PurchaseServlet.class.getName());

    private final PurchaseHistoryService purchaseService;

    public PurchaseServlet( ) {
        purchaseService = new PurchaseHistoryServiceImpl();
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }

        String gameIdParam = req.getParameter("gameId");
        String priceParam = req.getParameter("price");

        if (gameIdParam == null || priceParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "gameId or price not specified");
            return;
        }

        try {
            Long gameId = Long.parseLong(gameIdParam);
            int price = Integer.parseInt(priceParam);
            purchaseService.CreatePurchase(new CreatePurchaseHistoryRequest(
                    price,
                    user.getUserId(),
                    gameId
            ));
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Wrong number format");
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error to purchase board game");
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
