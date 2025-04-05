package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.entities.Toy;
import ru.vsu.amm.java.exception.DbException;
import ru.vsu.amm.java.repository.ToyRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

@WebServlet(name = "AddToyServlet", urlPatterns = "/addToy")
public class AddToyServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        BigDecimal price = new BigDecimal(req.getParameter("price"));
        Toy toy = new Toy(null, name, price);
        ToyRepository toyRepository = new ToyRepository();
        try {
            toyRepository.save(toy);
            resp.sendRedirect("index.jsp");
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
}
