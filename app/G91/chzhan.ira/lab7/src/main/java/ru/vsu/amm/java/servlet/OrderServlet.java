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
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.logging.Logger;
import java.util.logging.Level;

@WebServlet(name = "OrderServlet", urlPatterns = "/order")
public class OrderServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(OrderServlet.class.getName());

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        try {
            Long toyId = Long.parseLong(req.getParameter("toyId"));
            int quantity = Integer.parseInt(req.getParameter("quantity"));
            BigDecimal price = new BigDecimal(req.getParameter("price"));

            HttpSession session = req.getSession();
            Customer customer = (Customer) session.getAttribute("customer");

            if (customer == null) {
                resp.sendRedirect("/login.jsp");
                return;
            }

            BigDecimal totalPrice = price.multiply(new BigDecimal(quantity));

            Order order = new Order(null, customer.getId(), toyId, quantity, totalPrice);
            OrderRepository orderRepository = new OrderRepository();

            orderRepository.save(order);
            resp.sendRedirect("/orders");

        } catch (NumberFormatException e) {
            logger.log(Level.SEVERE, "Ошибка при преобразовании чисел: " + e.getMessage(), e);
            req.setAttribute("errorMessage", "Ошибка: Некорректный формат данных.");
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Ошибка при работе с базой данных: " + e.getMessage(), e);
            req.setAttribute("errorMessage", "Ошибка: Проблемы с базой данных.");
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
            logger.log(Level.SEVERE, "Неизвестная ошибка: " + e.getMessage(), e);
            req.setAttribute("errorMessage", "Ошибка: Что-то пошло не так.");
            getServletContext().getRequestDispatcher("/index.jsp").forward(req, resp);
        }
    }
}
