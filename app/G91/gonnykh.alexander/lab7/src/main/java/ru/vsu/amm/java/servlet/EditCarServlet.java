package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.enums.CarClass;
import ru.vsu.amm.java.enums.Status;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.model.dto.CarDto;
import ru.vsu.amm.java.service.implementations.CarService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "EditCarServlet", urlPatterns = "/updateCar/*")
public class EditCarServlet extends HttpServlet {
    private static final String MANAGE_CARS_PAGE = "/manageCars.jsp";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String CAR_LIST_ATTRIBUTE = "cars";

    private final CarService carService = new CarService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        List<String> errorMessages = new ArrayList<>();

        if (pathInfo == null || pathInfo.length() <= 1) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing carId in URL");
            return;
        }

        try {
            Long carId = Long.parseLong(pathInfo.substring(1));

            String manufacturer = request.getParameter("manufacturer");
            String model = request.getParameter("model");
            String yearParam = request.getParameter("year");
            String status = request.getParameter("status");
            String carClass = request.getParameter("carClass");

            int year = Integer.parseInt(yearParam);

            CarDto carDto = new CarDto(carId, manufacturer, model, year,
                    Status.valueOf(status), CarClass.valueOf(carClass));

            carService.updateCar(carDto);
        } catch (IllegalArgumentException | DataAccessException e) {
            errorMessages.add(e.getMessage());
        }

        try {
            request.setAttribute(CAR_LIST_ATTRIBUTE, carService.getAllCars());
        } catch (DataAccessException e) {
            errorMessages.add(e.getMessage());
        }

        if (!errorMessages.isEmpty()) {
            request.setAttribute(ERROR_MESSAGE, String.join("<br/>", errorMessages));
        }

        request.getRequestDispatcher(MANAGE_CARS_PAGE).forward(request, response);
    }
}
