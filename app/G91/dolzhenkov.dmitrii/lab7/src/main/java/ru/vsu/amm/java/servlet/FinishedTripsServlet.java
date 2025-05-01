package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.dto.TripDto;
import ru.vsu.amm.java.service.implementations.TripServiceImpl;
import ru.vsu.amm.java.service.interfaces.TripService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/finishedTrips")
public class FinishedTripsServlet extends HttpServlet {

    private final TripService tripService;

    public FinishedTripsServlet() {
        this.tripService = new TripServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = (String) req.getSession().getAttribute("login");

        if (login == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        List<TripDto> trips = tripService.getFinishTripsByUser(login);
        req.setAttribute("trips", trips);
        req.getRequestDispatcher("finishedTrips.jsp").forward(req, resp);
    }
}