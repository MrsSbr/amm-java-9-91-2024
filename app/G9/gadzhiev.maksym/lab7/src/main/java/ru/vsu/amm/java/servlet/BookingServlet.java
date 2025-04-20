package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.Exceptions.BookingException;
import ru.vsu.amm.java.entity.RealEstate;
import ru.vsu.amm.java.repository.RealEstateRepository;
import ru.vsu.amm.java.service.BookingService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

@WebServlet(urlPatterns = "/booking")
public class BookingServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long estateId = Long.parseLong(req.getParameter("estateId"));
        RealEstateRepository realEstateRepository = new RealEstateRepository();
        try {
            Optional<RealEstate> realEstate = realEstateRepository.findById(estateId);
            RealEstate estate = realEstate.get();
            req.setAttribute("estate", estate);
            req.getRequestDispatcher("/booking.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long estateId = Long.parseLong(req.getParameter("estateId"));
        LocalDate checkIn = LocalDate.parse(req.getParameter("checkIn"));
        LocalDate checkOut = LocalDate.parse(req.getParameter("checkOut"));
        int countGuests = Integer.parseInt(req.getParameter("guests"));

        BookingService bookingService = new BookingService();
        HttpSession httpSession = req.getSession();
        String userLogin = (String) httpSession.getAttribute("user");
        try{
            bookingService.createBooking(userLogin, estateId, checkIn, checkOut, countGuests);
            resp.sendRedirect("/profile");
        } catch (BookingException e) {
            req.setAttribute("error", e.getMessage());
            req.getRequestDispatcher("/booking.jsp").forward(req, resp);
        }
    }
}
