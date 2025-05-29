package ru.vsu.amm.java.Servlet;

import ru.vsu.amm.java.DBConnection.DBConfiguration;
import ru.vsu.amm.java.Exeption.UnCorrectDataException;
import ru.vsu.amm.java.Repository.StocksRepository;
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
        stocksService = new StocksService(new StocksRepository(DBConfiguration.getDataSource()));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        try {
            int userId = (int) req.getSession().getAttribute("userId");
            int stockId = Integer.parseInt(req.getParameter("stockId"));
            int count = Integer.parseInt(req.getParameter("count"));
            stocksService.sell(userId, stockId, count);
            resp.sendRedirect("/get-my");
        } catch (UnCorrectDataException e) {
            req.getSession().setAttribute("error","Нельзя продать больше акций чем есть");
            resp.sendRedirect("/get-my");
        } catch (SQLException | RuntimeException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }
}
