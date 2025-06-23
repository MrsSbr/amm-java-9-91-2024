package ru.vsu.amm.java.Servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.Entities.User;
import ru.vsu.amm.java.Responses.OrdersResponse;
import ru.vsu.amm.java.Services.OrderService;

import java.io.IOException;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() {
        orderService = new OrderService();
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        boolean isPaid = Boolean.parseBoolean(req.getParameter("isPaid"));

        OrdersResponse response;

        if (isPaid) {
            response = orderService.getPaidOrders(user);
        } else {
            response = orderService.getNotPaidOrders(user);
        }

        session.setAttribute("ordersResponse", response);
        session.setAttribute("isPaid", isPaid);
        getServletContext().getRequestDispatcher("/orders.jsp").forward(req, resp);
    }

}
