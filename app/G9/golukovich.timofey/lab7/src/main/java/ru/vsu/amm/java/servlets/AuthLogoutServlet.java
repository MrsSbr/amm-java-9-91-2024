package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.dtos.EmployeeDto;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/logout")
public class AuthLogoutServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(AuthLogoutServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("employee") != null) {
            EmployeeDto employee = (EmployeeDto) session.getAttribute("employee");
            session.invalidate();
            logger.info("Employee " + employee.getLogin() + " logged out");
        }
        resp.sendRedirect("/login");
    }
}