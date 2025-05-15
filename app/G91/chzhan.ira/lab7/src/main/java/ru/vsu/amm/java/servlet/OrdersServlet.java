package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.entities.Customer;
import ru.vsu.amm.java.entities.Order;
import ru.vsu.amm.java.exception.DbException;
import ru.vsu.amm.java.repository.OrderRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

@WebServlet(name = "OrdersServlet", urlPatterns = "/orders")
public class OrdersServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Customer customer = (Customer) session.getAttribute("customer");

        if (customer == null) {
            resp.sendRedirect("/login.jsp");
            return;
        }

        OrderRepository orderRepository = new OrderRepository();
        List<Order> orders;
        try {
            orders = orderRepository.findByCustomerId(customer.getId());
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }

        req.setAttribute("orders", orders); // Устанавливаем атрибут "orders" в запросе
        getServletContext().getRequestDispatcher("/orders.jsp").forward(req, resp);
    }
}
