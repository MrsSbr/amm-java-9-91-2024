package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.service.implementations.CarService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "DeleteCarServlet", urlPatterns = "/deleteCar")
public class DeleteCarServlet extends HttpServlet {

    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MANAGE_CARS_PAGE = "/manageCars.jsp";
    private static final String CAR_LIST_ATTRIBUTE = "cars";

    private final CarService carService = new CarService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<String> errorMessages = new ArrayList<>();

        String carIdParam = request.getParameter("carId");

        try {

            Long carId = Long.parseLong(carIdParam);
            carService.deleteCar(carId);
        } catch (IllegalArgumentException | DataAccessException e) {
            errorMessages.add(e.getMessage());
        }

        try {
            request.setAttribute(CAR_LIST_ATTRIBUTE, carService.findAllNotRentedCars());
        } catch (DataAccessException e) {
            errorMessages.add(e.getMessage());
        }

        if (!errorMessages.isEmpty()) {
            request.setAttribute(ERROR_MESSAGE, String.join("<br/>", errorMessages));
        }

        request.getRequestDispatcher(MANAGE_CARS_PAGE).forward(request, response);
    }
}
