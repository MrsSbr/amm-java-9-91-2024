package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.entity.Currency;
import ru.vsu.amm.java.exception.DataNotFoundException;
import ru.vsu.amm.java.exception.DatabaseException;
import ru.vsu.amm.java.service.CurrencyService;
import ru.vsu.amm.java.service.ExchangeService;
import ru.vsu.amm.java.service.impl.CurrencyServiceImpl;
import ru.vsu.amm.java.service.impl.ExchangeServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet(name = "CurrenciesServlet", urlPatterns = "/currencies")
public class CurrenciesServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CurrencyService currencyService = new CurrencyServiceImpl();
        List<Currency> currencies = currencyService.getAllCurrencies();

        req.setAttribute("currencies", currencies);
        req.getRequestDispatcher("/currencies.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String firstCurrencyCode = req.getParameter("cur1");
            String secondCurrencyCode = req.getParameter("cur2");
            int amount = Integer.parseInt(req.getParameter("amount"));
            ExchangeService exchangeService = new ExchangeServiceImpl();
            BigDecimal result = exchangeService.exchangeCurrencies(
                    firstCurrencyCode, secondCurrencyCode, amount);
            req.setAttribute("result", result.toString() + " " + secondCurrencyCode);
        } catch (DatabaseException | DataNotFoundException e) {
            req.setAttribute("errorMessage", e.getMessage());
        }
        CurrencyService currencyService = new CurrencyServiceImpl();
        List<Currency> currencies = currencyService.getAllCurrencies();
        req.setAttribute("currencies", currencies);

        req.getRequestDispatcher("/currencies.jsp").forward(req, resp);

    }
}
