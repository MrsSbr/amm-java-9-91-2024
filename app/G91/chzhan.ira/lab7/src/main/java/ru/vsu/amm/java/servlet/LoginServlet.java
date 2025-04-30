package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.entities.Customer;
import ru.vsu.amm.java.exception.DbException;
import ru.vsu.amm.java.service.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        AuthenticationService authService = new AuthenticationService();
        try {
            Optional<Customer> customerOptional = authService.login(name, password);
            if (customerOptional.isPresent()) {
                Customer customer = customerOptional.get();
                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("customer", customer);
                resp.sendRedirect("/index.jsp");
            } else {
                req.setAttribute("errorMessage", "Uncorrect username or password");
                getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } catch (DbException e) {
            System.out.println(e.getMessage());
            req.setAttribute("errorMessage", "Ошибка при подключении к базе данных");
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}

