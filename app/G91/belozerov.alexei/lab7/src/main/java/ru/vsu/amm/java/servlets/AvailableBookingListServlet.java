package ru.vsu.amm.java.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import ru.vsu.amm.java.models.dto.BookingDto;
import ru.vsu.amm.java.services.bookings.BookingService;
import ru.vsu.amm.java.services.bookings.impl.BookingServiceImpl;

import java.io.IOException;
import java.util.List;

import static ru.vsu.amm.java.utils.ApplicationConstants.AVAILABLE_BOOKINGS_PAGE;
import static ru.vsu.amm.java.utils.ApplicationConstants.AVAILABLE_BOOKINGS_URL;
import static ru.vsu.amm.java.utils.ApplicationConstants.LOGIN_URL;

@WebServlet(name = "AvailableBookingListServlet", urlPatterns = AVAILABLE_BOOKINGS_URL)
public class AvailableBookingListServlet extends HttpServlet {

    private final BookingService bookingService;

    public AvailableBookingListServlet() {
        this.bookingService = new BookingServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession httpSession = req.getSession(false);
        if (httpSession == null || httpSession.getAttribute("email") == null) {
            resp.sendRedirect(req.getContextPath() + LOGIN_URL);
            return;
        }
        Long flightId = Long.parseLong((String) httpSession.getAttribute("flightId"));
        List<String> availableSeats = bookingService.getAvailableSeatsByFlightId(flightId);

        req.setAttribute("availableSeats", availableSeats);
        getServletContext()
                .getRequestDispatcher(AVAILABLE_BOOKINGS_PAGE)
                .forward(req, resp);
    }
}
