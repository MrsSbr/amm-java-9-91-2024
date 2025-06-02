package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.utils.ServletMessageHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/main")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession(false);

        ServletMessageHelper.CopyErrorMessage(req, session);
        ServletMessageHelper.CopySuccessMessage(req, session);

        getServletContext().getRequestDispatcher("/main.jsp").forward(req, resp);
    }
}
