package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.exceptions.DatabaseException;
import ru.vsu.amm.java.services.HotelsService;
import ru.vsu.amm.java.services.impl.HotelsServiceImpl;
import ru.vsu.amm.java.utils.ServletMessageHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/hotels_management")
public class HotelsManagementServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var session = req.getSession(false);
        try {
            HotelsService hotelsService = new HotelsServiceImpl();
            var hotels = hotelsService.getAllHotels();

            req.setAttribute("hotels_table", hotels);

            ServletMessageHelper.CopyErrorMessage(req, session);
            ServletMessageHelper.CopySuccessMessage(req, session);

            getServletContext().getRequestDispatcher("/hotels_dashboard.jsp").forward(req, resp);
        } catch (DatabaseException e) {
            session.setAttribute("errorMessage", "Internal server error! Try again later");
            getServletContext().getRequestDispatcher("/api/main").forward(req, resp);
        }
    }
}
