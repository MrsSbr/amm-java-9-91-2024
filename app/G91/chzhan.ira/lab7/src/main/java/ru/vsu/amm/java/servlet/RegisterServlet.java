package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.entities.Customer;
import ru.vsu.amm.java.exception.DbException;
import ru.vsu.amm.java.exception.NotFoundException;
import ru.vsu.amm.java.exception.PasswordNotMatchException;
import ru.vsu.amm.java.service.AuthenticationService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Optional;

@WebServlet(name = "RegisterServlet", urlPatterns = "/register")
public class RegisterServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String password = req.getParameter("password");

        AuthenticationService authService = new AuthenticationService();

        try {
            authService.register(name, password); // Регистрация пользователя

            // После успешной регистрации нужно получить объект Customer из базы
            Optional<Customer> customerOptional = new ru.vsu.amm.java.repository.CustomerRepository().findByName(name);
            if(customerOptional.isPresent()) {
                Customer customer = customerOptional.get();

                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("customer", customer); // Сохраняем объект Customer
                resp.sendRedirect("/index.jsp");
            } else {
                req.setAttribute("errorMessage", "Ошибка при получении данных пользователя.");
                getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
            }
        } catch (Exception e) {
            req.setAttribute("errorMessage", "Ошибка при регистрации: " + e.getMessage());
            getServletContext().getRequestDispatcher("/register.jsp").forward(req, resp);
        }
    }
}
