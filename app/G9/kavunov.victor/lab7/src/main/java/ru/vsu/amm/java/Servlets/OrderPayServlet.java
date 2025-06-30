package ru.vsu.amm.java.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.Entities.User;
import ru.vsu.amm.java.Services.OrderService;

import java.io.IOException;

@WebServlet("/order/pay")
public class OrderPayServlet extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() {
        orderService = new OrderService();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        orderService.payAllOrders(user);

        resp.sendRedirect("/order?isPaid=true");
    }
}
