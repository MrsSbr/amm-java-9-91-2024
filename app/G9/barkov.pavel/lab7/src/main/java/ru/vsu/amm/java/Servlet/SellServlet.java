package ru.vsu.amm.java.Servlet;

import ru.vsu.amm.java.Service.StocksService;

import javax.servlet.Servlet;
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
            int userId = (int) req.getAttribute("UserId");
            int stockId = (int) req.getAttribute("StockId");
            int count = (int) req.getAttribute("Count");
            stocksService.sell(userId, stockId, count);
        } catch (SQLException | RuntimeException e) {
            //Обработка исключений
        }
    }
}
