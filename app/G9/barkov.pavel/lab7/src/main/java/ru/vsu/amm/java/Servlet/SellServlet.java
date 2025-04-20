package ru.vsu.amm.java.Servlet;

import ru.vsu.amm.java.Service.StocksService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "Sell", urlPatterns = "/sell")
public class SellServlet extends HttpServlet {
    private StocksService stocksService;

    public SellServlet() {
        stocksService = new StocksService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int userId = (int) req.getSession().getAttribute("userId");
            int stockId = Integer.parseInt(req.getParameter("stockId"));
            int count = Integer.parseInt(req.getParameter("count"));
            stocksService.sell(userId, stockId, count);
            resp.sendRedirect("/get-my");
        } catch (SQLException | RuntimeException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }
}
