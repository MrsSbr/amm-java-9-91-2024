package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.entities.Customer;
import ru.vsu.amm.java.service.ToyService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "DeleteToyServlet", urlPatterns = "/deleteToy")
public class DeleteToyServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();
        Customer customer = (Customer) session.getAttribute("customer");

        // Check if the user is logged in and is the admin
        if (customer == null || !"admin".equals(customer.getName())) {
            resp.sendRedirect("/login.jsp");
            return;
        }

        String toyId = req.getParameter("toyId");

        ToyService toyService = new ToyService();
        try {
            toyService.deleteToy(toyId);
            resp.sendRedirect("index.jsp");
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Ошибка удаления игрушки: " + e.getMessage());
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
