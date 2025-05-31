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

@WebServlet(name = "AddCarServlet", urlPatterns = "/addCar")
public class AddCarServlet extends HttpServlet {

    private static final String MANAGE_CARS_PAGE = "/manageCars.jsp";
    private static final String ERROR_MESSAGE = "errorMessage";

    private final CarService carService = new CarService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String manufacturer = request.getParameter("manufacturer");
        String model = request.getParameter("model");
        String yearStr = request.getParameter("year");
        String status = request.getParameter("status");
        String carClass = request.getParameter("carClass");

        try {
            int year = Integer.parseInt(yearStr);

            CarDto carDto = new CarDto(
                    null,
                    manufacturer,
                    model,
                    year,
                    Status.valueOf(status),
                    CarClass.valueOf(carClass)
            );

            carService.addCar(carDto);
            response.sendRedirect("/manageCars");

        } catch (IllegalArgumentException | DataAccessException e) {
            request.setAttribute(ERROR_MESSAGE, e);
            getServletContext().getRequestDispatcher(MANAGE_CARS_PAGE).forward(request, response);
        }
    }
}
