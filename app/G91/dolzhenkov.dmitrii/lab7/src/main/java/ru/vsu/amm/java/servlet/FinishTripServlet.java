package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.dto.TripEndDto;
import ru.vsu.amm.java.service.implementations.TripServiceImpl;
import ru.vsu.amm.java.service.interfaces.TripService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/finishTrip")
public class FinishTripServlet extends HttpServlet {
    private final TripService tripService;

    public FinishTripServlet() {
        this.tripService = new TripServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userLogin = (String) req.getSession().getAttribute("login");
        if (userLogin == null) {
            resp.sendRedirect("login.jsp");
            return;
        }

        List<TripEndDto> trips = tripService.getActiveTripsByUser(userLogin);
        req.setAttribute("trips", trips);
        req.getRequestDispatcher("finishTrip.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            Long tripId = Long.parseLong(req.getParameter("tripId"));
            Double latitude = Double.parseDouble(req.getParameter("latitude"));
            Double longitude = Double.parseDouble(req.getParameter("longitude"));

            tripService.finishTrip(tripId, latitude, longitude);

        } catch (Exception e) {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Ошибка завершения поездки");
            return;
        }

        resp.sendRedirect("finishTrip");
    }
}
