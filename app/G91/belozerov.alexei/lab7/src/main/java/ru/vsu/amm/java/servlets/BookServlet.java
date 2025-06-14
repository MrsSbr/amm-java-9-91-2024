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
import ru.vsu.amm.java.services.clients.ClientService;
import ru.vsu.amm.java.services.clients.impl.ClientServiceImpl;

import java.io.IOException;

import static ru.vsu.amm.java.utils.ApplicationConstants.BOOK_PAGE;
import static ru.vsu.amm.java.utils.ApplicationConstants.BOOK_URL;
import static ru.vsu.amm.java.utils.ApplicationConstants.CLIENT_BOOKINGS_URL;
import static ru.vsu.amm.java.utils.ApplicationConstants.LOGIN_URL;

@WebServlet(name = "BookServlet", urlPatterns = BOOK_URL)
public class BookServlet extends HttpServlet {

    private final BookingService bookingService;
    private final ClientService clientService;

    public BookServlet() {
        this.bookingService = new BookingServiceImpl();
        this.clientService = new ClientServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher(BOOK_PAGE).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession httpSession = req.getSession(false);
        if (httpSession == null || httpSession.getAttribute("email") == null) {
            resp.sendRedirect(req.getContextPath() + LOGIN_URL);
            return;
        }

        Long flightId = Long.parseLong((String) httpSession.getAttribute("flightId"));
        String email = (String) httpSession.getAttribute("email");
        String seatNumber = (String) httpSession.getAttribute("seatNumber");
        String ticketNumber = (String) httpSession.getAttribute("ticketNumber");

        Long clientId = clientService.getClientIdByEmail(email).orElseThrow();

        BookingDto bookingDto = new BookingDto(ticketNumber, seatNumber, flightId, clientId);
        bookingService.bookSeat(bookingDto);

        resp.sendRedirect(req.getContextPath() + CLIENT_BOOKINGS_URL);
    }
}
