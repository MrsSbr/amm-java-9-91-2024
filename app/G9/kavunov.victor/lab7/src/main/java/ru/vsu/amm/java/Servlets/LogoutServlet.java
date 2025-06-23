package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Entities.User;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.logging.Logger;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(LogoutServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        if (session != null && session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            String loginUsed = (String) session.getAttribute("loginUsed");

            if (loginUsed == null) {
                loginUsed = user.getEmail();
            }

            session.invalidate();
            logger.info(String.format("User %s logged out", loginUsed));
        }
        resp.sendRedirect("/login");
    }
}