package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.service.ProfileService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/profile")
public class ProfileServlet extends HttpServlet {
    private final ProfileService profileService = new ProfileService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession httpSession = req.getSession();
        String userLogin = (String) httpSession.getAttribute("user");
        System.out.println("username");
        try {
            req.setAttribute("bookings", profileService.getUserBookings(userLogin));
            req.getRequestDispatcher("/profile.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        String action = req.getParameter("action");
        if ("cancel".equals(action)) {
            handleCancelBooking(req, resp);
        }
    }

    private void handleCancelBooking(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Long bookingId = Long.parseLong(req.getParameter("bookingId"));
            profileService.cancelBooking(bookingId);
            resp.sendRedirect("/profile?cancelSuccess=true");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
