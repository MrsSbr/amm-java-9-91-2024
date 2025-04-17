package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.entities.CarEntity;
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
import java.util.List;

@WebServlet(name = "GetAvailableCars", urlPatterns = "/availableCars")
public class GetAvailableCarsServlet extends HttpServlet {
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MENU_RENT_CARS_PAGE = "/menuRentCars.jsp";
    private final CarService carService = new CarService();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<CarDto> cars = carService.getCarByStatus(Status.AVAILABLE);
            request.setAttribute("cars", cars);
            getServletContext().getRequestDispatcher(MENU_RENT_CARS_PAGE)
                    .forward(request, response);
        } catch (DataAccessException e) {
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            getServletContext().getRequestDispatcher(MENU_RENT_CARS_PAGE)
                    .forward(request, response);
        }
    }

}
