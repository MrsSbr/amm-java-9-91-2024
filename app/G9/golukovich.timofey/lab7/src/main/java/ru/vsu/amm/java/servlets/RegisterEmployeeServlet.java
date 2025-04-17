package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.exceptions.EmployeeAlreadyExistsException;
import ru.vsu.amm.java.exceptions.PostDoesNotExistException;
import ru.vsu.amm.java.services.HotelsAdminService;
import ru.vsu.amm.java.services.impl.HotelsAdminServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/register_employee")
public class RegisterEmployeeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/register_employee.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = req.getParameter("login");
        String password = req.getParameter("password");
        String post = req.getParameter("post");

        HotelsAdminService hotelsAdminService = new HotelsAdminServiceImpl();
        try {
            hotelsAdminService.registerEmployee(login, password, post);
            resp.sendRedirect("/index.jsp");
        } catch (EmployeeAlreadyExistsException | PostDoesNotExistException e) {
            req.setAttribute("errorMessage", e.getMessage());
            getServletContext().getRequestDispatcher("/register_employee.jsp").forward(req, resp);
        } catch (DatabaseException e) {
            req.setAttribute("errorMessage", "Internal server error! Try again later");
            getServletContext().getRequestDispatcher("/register_employee.jsp").forward(req, resp);
        }
    }
}
