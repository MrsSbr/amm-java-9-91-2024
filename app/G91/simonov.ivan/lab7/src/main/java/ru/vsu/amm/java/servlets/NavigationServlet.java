package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.enums.Role;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/redirectUser")
public class NavigationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        String redirect = switch (user.getRole()) {
            case Role.USER -> "viewSessions";
            case Role.EMPLOYEE -> "employeeActions.jsp";
            case Role.ADMIN -> "adminActions.jsp";
        };

        response.sendRedirect(redirect);

    }
}
