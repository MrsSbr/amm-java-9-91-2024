package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.models.dto.FlightDto;
import ru.vsu.amm.java.services.flights.FlightService;
import ru.vsu.amm.java.services.flights.impl.FlightServiceImpl;

import java.io.IOException;
import java.util.List;

import static ru.vsu.amm.java.utils.ApplicationConstants.FLIGHTS_PAGE;
import static ru.vsu.amm.java.utils.ApplicationConstants.FLIGHTS_URL;
import static ru.vsu.amm.java.utils.ApplicationConstants.LOGIN_URL;

@WebServlet(name = "FlightListServlet", urlPatterns = FLIGHTS_URL)
public class FlightListServlet extends HttpServlet {

    private final FlightService flightService;

    public FlightListServlet() {
        this.flightService = new FlightServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession httpSession = req.getSession(false);
        if (httpSession == null || httpSession.getAttribute("email") == null) {
            resp.sendRedirect(req.getContextPath() + LOGIN_URL);
            return;
        }

        List<FlightDto> flights = flightService.getAllFlights();

        req.setAttribute("flights", flights);

        getServletContext()
                .getRequestDispatcher(FLIGHTS_PAGE)
                .forward(req, resp);
    }
}
