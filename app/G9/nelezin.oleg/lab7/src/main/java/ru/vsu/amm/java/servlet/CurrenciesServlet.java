package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.entity.Currency;
import ru.vsu.amm.java.service.CurrencyService;
import ru.vsu.amm.java.service.impl.CurrencyServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CurrenciesServlet", urlPatterns = "/currencies")
public class CurrenciesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CurrencyService currencyService = new CurrencyServiceImpl();
        List<Currency> currencies = currencyService.getAllCurrencies();

        System.out.println(currencies.size());
        req.setAttribute("currencies", currencies);
        req.getRequestDispatcher("/currencies.jsp").forward(req, resp);
    }
}
