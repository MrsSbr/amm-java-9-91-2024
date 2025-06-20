package ru.vsu.amm.java.servlets;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.vsu.amm.java.utils.ApplicationConstants;

import java.io.IOException;

import static ru.vsu.amm.java.utils.ApplicationConstants.LOGOUT_URL;

@WebServlet(name = "LogoutServlet", urlPatterns = LOGOUT_URL)
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.getSession().invalidate();
        resp.sendRedirect(req.getContextPath() + ApplicationConstants.LOGIN_URL);
    }
}

