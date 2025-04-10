package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.enums.UserRole;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.exceptions.WrongUserCredentialsException;
import ru.vsu.amm.java.model.requests.ScooterRequest;
import ru.vsu.amm.java.model.requests.UserRequest;
import ru.vsu.amm.java.service.implementations.ScooterServiceImpl;
import ru.vsu.amm.java.service.implementations.UserAuthManager;
import ru.vsu.amm.java.service.interfaces.AuthService;
import ru.vsu.amm.java.service.interfaces.ScooterService;
import ru.vsu.amm.java.utils.ErrorMessages;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "ScooterAddServlet", urlPatterns = "/addScooter")
public class ScooterAddServlet extends HttpServlet {
    private static final String ADD_SCOOTER_VIEW = "/addScooter.jsp";
    private static final String MAIN_UI  = "/index.jsp";
    private final ScooterService scooterService;

    public ScooterAddServlet() {
        this.scooterService = new ScooterServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        showAccessInterface(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String model = request.getParameter("model");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");

        try {
            scooterService.addScooter(new ScooterRequest(model, Double.parseDouble(latitude), Double.parseDouble(longitude)));

            response.sendRedirect(ADD_SCOOTER_VIEW);
        } catch (DataAccessException e) {
            request.setAttribute(ErrorMessages.ERROR_MESSAGE, e.getMessage());
            getServletContext().getRequestDispatcher(ADD_SCOOTER_VIEW).forward(request, response);
        }
    }

    private void showAccessInterface(HttpServletRequest request,
                                     HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher(ADD_SCOOTER_VIEW).forward(request, response);
    }
}