package ru.vsu.amm.java.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

@WebServlet("/")
public class RootServlet extends HttpServlet {
    private static final Logger logger = Logger.getLogger(RootServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.log(Level.FINE, "Запрос корневой страницы, перенаправление на leaderboard");
        response.sendRedirect("/leaderboard");
    }
}
