package ru.vsu.amm.java.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.Services.SmartphoneService;

import java.io.IOException;

@WebServlet("/catalog")
public class CatalogServlet extends HttpServlet {
    private SmartphoneService smartphoneService;

    @Override
    public void init() {
        smartphoneService = new SmartphoneService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("smartphones", smartphoneService.getAll());
        getServletContext().getRequestDispatcher("/catalog.jsp").forward(req, resp);
    }
}
