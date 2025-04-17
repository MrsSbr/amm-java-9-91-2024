package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.dto.ScooterDto;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.service.implementations.ScooterServiceImpl;
import ru.vsu.amm.java.service.implementations.TripServiceImpl;
import ru.vsu.amm.java.service.interfaces.ScooterService;
import ru.vsu.amm.java.service.interfaces.TripService;
import ru.vsu.amm.java.utils.ErrorMessages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@WebServlet(name = "ScooterListServlet", urlPatterns = "/scooters")
public class ScooterListServlet extends HttpServlet {
    private static final String SCOOTER_LIST_VIEW = "/scooterList.jsp";
    private final ScooterService scooterService;
    private final TripService tripService;

    public ScooterListServlet() {
        this.scooterService = new ScooterServiceImpl();
        this.tripService=new TripServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<ScooterDto> scooters = scooterService.getAllScooters();
            request.setAttribute("scooters", scooters);
        } catch (Exception e) {
            request.setAttribute(ErrorMessages.ERROR_MESSAGE, e.getMessage());
        }

        request.getRequestDispatcher(SCOOTER_LIST_VIEW).forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        String login = (session != null) ? (String) session.getAttribute("login") : null;
        String idParam = request.getParameter("scooterId");

        if (login == null) {
            response.sendRedirect("signin.jsp");
            return;
        }

        try {
            Long scooterId = Long.parseLong(idParam);
            tripService.bookScooter(scooterId, login);
        } catch (NoSuchElementException | IllegalArgumentException e) {
            request.setAttribute(ErrorMessages.ERROR_MESSAGE, e.getMessage());
            request.getRequestDispatcher(SCOOTER_LIST_VIEW).forward(request, response);
            return;
        }

        response.sendRedirect("scooters");
    }
}