package ru.vsu.amm.java.servlet;

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

@WebServlet(name = "GetAllCarsServlet", urlPatterns = "/getAllCars")
public class GetAllCarsServlet extends HttpServlet {
    private final CarService carService = new CarService();
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String MANAGE_CARS_PAGE = "/manageCars.jsp";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try {
            List<CarDto> carList = carService.findAllCars();
            request.setAttribute("cars", carList);
        } catch (DataAccessException e) {
            request.setAttribute(ERROR_MESSAGE, e.getMessage());

        }
        getServletContext().getRequestDispatcher(MANAGE_CARS_PAGE)
                .forward(request, response);
    }
}
