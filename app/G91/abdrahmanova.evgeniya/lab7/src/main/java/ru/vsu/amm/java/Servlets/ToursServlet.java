package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Enums.Role;
import ru.vsu.amm.java.Exceptions.AuthorizationException;
import ru.vsu.amm.java.Exceptions.DatabaseException;
import ru.vsu.amm.java.Services.ToursService;
import ru.vsu.amm.java.Utils.ServletHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/tours")
public class ToursServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");
        req.setCharacterEncoding("UTF-8");

        final String torusAttributeName = "tours";
        try {
            var userDto = ServletHelper.getUser(req);

            ToursService toursService = new ToursService();
            var tours = toursService.getAllTours();

            if (userDto.userRole() == Role.USER) {
                var bookedTours = toursService.getAllToursBookedByUser(userDto.id());
                req.setAttribute("booked_tours", bookedTours);
            }

            req.setAttribute(torusAttributeName, tours);
        } catch (DatabaseException e) {
            req.setAttribute(torusAttributeName, new ArrayList<>());
        } catch (AuthorizationException e) {
            ServletHelper.setError(req, e.getMessage());
        }
        getServletContext().getRequestDispatcher("/tours.jsp").forward(req, resp);
    }
}
