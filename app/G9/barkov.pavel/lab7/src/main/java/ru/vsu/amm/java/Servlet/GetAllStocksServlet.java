package ru.vsu.amm.java.Servlet;

import ru.vsu.amm.java.DBConnection.DBConfiguration;
import ru.vsu.amm.java.Repository.Entities.Stocks;
import ru.vsu.amm.java.Repository.StocksRepository;
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

@WebServlet(name = "GetAll", urlPatterns = "/get-all")
public class GetAllStocksServlet extends HttpServlet {
    private static final int PAGE_SIZE = 5;
    private StocksService stocksService;

    public GetAllStocksServlet() {
        stocksService = new StocksService(new StocksRepository(DBConfiguration.getDataSource()));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int page = parsePageParameter(req);
            req.setAttribute("currentPage",page);
            List<Stocks> stocks = stocksService.getAll(PAGE_SIZE,(page-1)*PAGE_SIZE);
            int total = stocksService.count(PAGE_SIZE);
            req.setAttribute("totalPages", total);
            req.setAttribute("allStocks",stocks);
            RequestDispatcher dispatcher = req.getRequestDispatcher("/main.jsp");
            dispatcher.forward(req, resp);
        } catch (SQLException e){
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }

    private int parsePageParameter(HttpServletRequest req) {
        try {
            return Integer.parseInt(req.getParameter("page"));
        } catch (NumberFormatException | NullPointerException e) {
            req.setAttribute("currentPage",1);
            return 1;
        }
    }
}
