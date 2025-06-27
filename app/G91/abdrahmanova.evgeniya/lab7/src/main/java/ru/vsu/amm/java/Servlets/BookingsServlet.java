package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Exceptions.AuthorizationException;
import ru.vsu.amm.java.Exceptions.DatabaseException;
import ru.vsu.amm.java.Services.BookingsService;
import ru.vsu.amm.java.Utils.ServletHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet("/bookings")
public class BookingsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String bookingsAttributeName = "bookings";
        try {
            var userDto = ServletHelper.getUser(req);

            BookingsService bookingsService = new BookingsService();
            var bookings = bookingsService.getUserBookings(userDto.id());
//            var bookings = List.of(
//                    new BookingDto(123, 3, userDto.id(), LocalDate.now().minusYears(1), 2, 15000, Status.COMPLETED),
//                    new BookingDto(456, 4, userDto.id(), LocalDate.now().minusYears(2), 2, 13000, Status.FAILED),
//                    new BookingDto(789, 5, userDto.id(), LocalDate.now().minusYears(3), 2, 11000, Status.CONFIRMED)
//            );
//            var bookings = new ArrayList<BookingDto>();
            req.setAttribute(bookingsAttributeName, bookings);
        } catch (DatabaseException e) {
            req.setAttribute(bookingsAttributeName, new ArrayList<>());
        } catch (AuthorizationException e) {
            ServletHelper.setError(req, e.getMessage());
        }
        getServletContext().getRequestDispatcher("/bookings.jsp").forward(req, resp);
    }
}
