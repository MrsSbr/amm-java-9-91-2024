package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.User;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/authorization")
public class AuthorizationServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpSession session = request.getSession(false);
        User user = (session != null) ? (User) session.getAttribute("user") : null;

        if (user == null) {
            String redirectTo = request.getParameter("redirectTo");
            if (redirectTo == null || redirectTo.isEmpty()) {
                redirectTo = "index.jsp";
            }

            session = request.getSession();
            session.setAttribute("redirectTo", redirectTo);

            response.sendRedirect("authorization.jsp");
        } else {
            String redirectTo = request.getParameter("redirectTo");
            if (redirectTo != null && !redirectTo.isEmpty()) {
                response.sendRedirect(redirectTo);
            } else {
                response.sendRedirect("index.jsp");
            }
        }
    }
}