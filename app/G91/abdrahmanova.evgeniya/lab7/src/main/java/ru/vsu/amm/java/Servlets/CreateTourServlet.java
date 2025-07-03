package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Exceptions.AuthorizationException;
import ru.vsu.amm.java.Exceptions.DatabaseException;
import ru.vsu.amm.java.Exceptions.InvalidTourDataException;
import ru.vsu.amm.java.Services.ToursService;
import ru.vsu.amm.java.Utils.ServletHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/create_tour")
public class CreateTourServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/create_tour.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String duration = req.getParameter("duration");
        String price = req.getParameter("price");
        String maxParticipants = req.getParameter("maxParticipants");
        String startLocation = req.getParameter("startLocation");
        String languages = req.getParameter("languages");
        try {
            var guide = ServletHelper.getUser(req);

            ToursService toursService = new ToursService();
            toursService.createTour(guide, title, description, duration, price, maxParticipants, startLocation, languages);
            ServletHelper.setMessage(req, "Тур успешно создан!");
            resp.sendRedirect(req.getContextPath() + "/tours");
        } catch (InvalidTourDataException | DatabaseException e) {
            ServletHelper.setError(req, e.getMessage());
            getServletContext().getRequestDispatcher("/create_tour.jsp").forward(req, resp);
        } catch (AuthorizationException e) {
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
