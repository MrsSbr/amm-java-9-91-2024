package ru.vsu.amm.java.Servlet;

import ru.vsu.amm.java.Repository.Entities.Stocks;
import ru.vsu.amm.java.Service.StocksService;

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

    private StocksService stocksService;

    public GetAllStocksServlet() {
        stocksService = new StocksService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Stocks> stocks = stocksService.getAll();
            req.setAttribute("Stocks",stocks);
        } catch (SQLException e){
            //Обработка исключений
        }
    }
}
