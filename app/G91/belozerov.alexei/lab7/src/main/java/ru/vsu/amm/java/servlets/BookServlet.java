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
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("email") == null) {
            resp.sendRedirect(req.getContextPath() + LOGIN_URL);
            return;
        }

        Long flightId   = (Long) session.getAttribute("flightId");
        String email    = (String) session.getAttribute("email");
        String seat     = req.getParameter("seatNumber");
        String ticket   = req.getParameter("ticketNumber");

        if (flightId != null && email != null && seat != null && ticket != null) {
            Long clientId = clientService.getClientIdByEmail(email).orElse(null);
            if (clientId == null) {
                resp.sendError(404, "Клиент не найден");
                return;
            }
            bookingService.bookSeat(new BookingDto(ticket, seat, flightId, clientId));
        }
        resp.sendRedirect(req.getContextPath() + CLIENT_BOOKINGS_URL);
    }
}
