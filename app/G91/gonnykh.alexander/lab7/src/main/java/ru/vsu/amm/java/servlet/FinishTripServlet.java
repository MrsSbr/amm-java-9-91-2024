package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.service.implementations.UserCarService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.NoSuchElementException;

@WebServlet(name = "FinishTripServlet", urlPatterns = "/finishTrip")
public class FinishTripServlet extends HttpServlet {
    private static final String FINISH_TRIP_PAGE = "/finishTrip.jsp";
    private static final String ERROR_MESSAGE = "errorMessage";
    private final UserCarService userCarService = new UserCarService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Long tripId = Long.valueOf(request.getParameter("tripId"));
            BigDecimal finalPrice = userCarService.finishTrip(tripId);
            request.setAttribute("finalPrice", finalPrice);
            request.getRequestDispatcher(FINISH_TRIP_PAGE).forward(request, response);
        } catch (NumberFormatException | DataAccessException | NoSuchElementException e) {
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
        }
    }
}
