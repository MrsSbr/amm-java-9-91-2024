package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.UserEntity;
import ru.vsu.amm.java.responces.PurchaseHistoryResponce;
import ru.vsu.amm.java.service.PurchaseHistoryServiceImpl;
import ru.vsu.amm.java.service.interfaces.PurchaseHistoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/history")
public class HistoryServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(HistoryServlet.class.getName());

    private final PurchaseHistoryService purchaseService;

    public HistoryServlet() {
        purchaseService = new PurchaseHistoryServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        UserEntity user = (UserEntity) session.getAttribute("user");

        if (user == null) {
            resp.sendRedirect("/login");
            return;
        }

        try {
            List<PurchaseHistoryResponce> purchases = purchaseService.getUserPurchases(user.getUserId());
            purchases.sort(Comparator.comparingLong(PurchaseHistoryResponce::orderNumber).reversed());

            req.setAttribute("purchases", purchases);
            req.getRequestDispatcher("/history.jsp").forward(req, resp);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error trying to watch the purchase history");
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
