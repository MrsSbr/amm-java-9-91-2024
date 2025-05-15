package ru.vsu.amm.java.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.entity.Coffee;
import ru.vsu.amm.java.entity.User;
import ru.vsu.amm.java.exception.SqlException;
import ru.vsu.amm.java.repository.CoffeeRepository;
import ru.vsu.amm.java.service.CoffeeService;

import java.io.IOException;
import java.security.InvalidParameterException;

@WebServlet("/coffees/create")
public class CoffeeCreateServlet extends HttpServlet {
    private CoffeeService coffeeService;

    @Override
    public void init() {
        coffeeService = (CoffeeService) getServletContext().getAttribute("coffeeService");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        req.getRequestDispatcher("/coffeeCreate.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String title = req.getParameter("title");
            if (title == null || title.isBlank()) {
                throw new InvalidParameterException("title is null or empty");
            }

            String description = req.getParameter("description");
            if (description == null || description.isBlank()) {
                throw new InvalidParameterException("description is null or empty");
            }
            User author = (User) req.getSession(false).getAttribute("user");

            coffeeService.create(title, description, author);
        } catch (InvalidParameterException | SqlException e) {
            req.setAttribute("errorMessage", e.getMessage());
            req.getRequestDispatcher("/coffeeCreate.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect("/coffees");
    }
}
