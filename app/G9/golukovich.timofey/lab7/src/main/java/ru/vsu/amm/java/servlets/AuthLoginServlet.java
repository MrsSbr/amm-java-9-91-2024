package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.exceptions.EmployeeNotFoundException;
import ru.vsu.amm.java.services.AuthorizationService;
import ru.vsu.amm.java.services.impl.AuthorizationServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/login")
public class AuthLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");

        AuthorizationService authService = new AuthorizationServiceImpl();
        try {
            var employeeOpt = authService.login(login, password);
            if (employeeOpt.isPresent()) {
                HttpSession httpSession = req.getSession();
                httpSession.setAttribute("login", login);
                httpSession.setAttribute("post", employeeOpt.get().getPost());
                resp.sendRedirect("/index.jsp");
            } else {
                req.setAttribute("errorMessage", "Wrong login or password. Please, try again");
                getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
            }
        } catch (EmployeeNotFoundException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        } catch (DatabaseException e) {
            req.setAttribute("errorMessage", "Internal server error! Please, try again later");
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
