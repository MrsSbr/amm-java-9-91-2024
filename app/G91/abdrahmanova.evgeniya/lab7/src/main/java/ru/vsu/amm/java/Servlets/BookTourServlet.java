package ru.vsu.amm.java.Servlets;

import ru.vsu.amm.java.Exceptions.AuthorizationException;
import ru.vsu.amm.java.Exceptions.DatabaseException;
import ru.vsu.amm.java.Exceptions.InvalidBookingDataException;
import ru.vsu.amm.java.Exceptions.InvalidTourDataException;
import ru.vsu.amm.java.Services.BookingsService;
import ru.vsu.amm.java.Utils.ServletHelper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/book_tour")
public class BookTourServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String tourId = req.getParameter("tour_id");
        String date = req.getParameter("date");
        String participants = req.getParameter("participants");
        try {
            var user = ServletHelper.getUser(req);

            BookingsService bookingsService = new BookingsService();
            bookingsService.createBooking(user, tourId, date, participants);
            ServletHelper.setMessage(req, "Тур успешно создан!");
            resp.sendRedirect(req.getContextPath() + "/tours");
        } catch (InvalidBookingDataException | DatabaseException e) {
            ServletHelper.setError(req, e.getMessage());
            resp.sendRedirect(req.getContextPath() + "/tours");
        } catch (AuthorizationException e) {
            getServletContext().getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }
}
