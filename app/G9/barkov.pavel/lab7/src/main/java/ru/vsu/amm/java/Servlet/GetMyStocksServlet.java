package ru.vsu.amm.java.Servlet;

import ru.vsu.amm.java.Repository.Entities.Stocks;
import ru.vsu.amm.java.Service.StocksService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "GetMy", urlPatterns = "/get-my")
public class GetMyStocksServlet extends HttpServlet {
    private static final int PAGE_SIZE = 5;
    private StocksService stocksService;

    public GetMyStocksServlet() {
        stocksService = new StocksService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int userId = (int)req.getSession().getAttribute("userId");
            int page = parsePageParameter(req);
            req.setAttribute("currentPage",page);
            List<Stocks> stocks = stocksService.getAll(userId,PAGE_SIZE,(page-1)*PAGE_SIZE);
            int total = (stocksService.count(userId)+PAGE_SIZE-1) / PAGE_SIZE;
            req.setAttribute("totalPages", total);
            req.setAttribute("myStocks", stocks);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/home.jsp");
            dispatcher.forward(req, resp);

        } catch (SQLException | RuntimeException e) {
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }
    private int parsePageParameter(HttpServletRequest req) {
        try {
            return Integer.parseInt(req.getParameter("page"));
        } catch (NumberFormatException | NullPointerException e) {
            return 1;
        }
    }
}
