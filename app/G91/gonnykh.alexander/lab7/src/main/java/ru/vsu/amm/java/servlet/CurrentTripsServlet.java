package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.model.dto.TripDto;
import ru.vsu.amm.java.service.implementations.UserCarService;
import ru.vsu.amm.java.service.implementations.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CurrentTripsServlet", urlPatterns = "/trips")
public class CurrentTripsServlet extends HttpServlet {
    private final UserService userService = new UserService();
    private final UserCarService userCarService = new UserCarService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String email = (String) session.getAttribute("email");
        try {
            Long customerId = userService.getUserByEmail(email).getId();
            List<TripDto> trips = userCarService.getCurrentTripsByCustomerId(customerId);
            request.setAttribute("trips", trips);
            request.getRequestDispatcher("/trips.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("errorMessage", "Не удалось загрузить поездки: " + e.getMessage());
            request.getRequestDispatcher("/trips.jsp").forward(request, response);
        }
    }
}
