package ru.vsu.amm.java.servlet;

import ru.vsu.amm.java.enums.Status;
import ru.vsu.amm.java.exceptions.DataAccessException;
import ru.vsu.amm.java.model.dto.CarDto;
import ru.vsu.amm.java.service.implementations.CarService;
import ru.vsu.amm.java.service.implementations.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@WebServlet(name = "BookCarServlet", urlPatterns = "/bookCar")
public class BookCarServlet extends HttpServlet {
    private final CarService carService = new CarService();
    private final UserService userService = new UserService();
    private static final String MENU_RENT_CARS = "/menuRentCars.jsp";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String SUCCESS_MESSAGE = "successMessage";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Long carId = Long.parseLong(request.getParameter("carId"));
        String email = (String) session.getAttribute("email");
        try {
            Long customerId = userService.getUserByEmail(email).getId();
            CarDto carDto = carService.getCarById(carId);
            carService.bookCar(customerId, carDto);
            request.setAttribute(SUCCESS_MESSAGE, "Car successfully booked!");
        } catch (DataAccessException e) {
            request.setAttribute(ERROR_MESSAGE, "An error occurred while accessing the data.");
        } catch (NoSuchElementException e) {
            request.setAttribute(ERROR_MESSAGE, "Car not found.");
        } catch (IllegalArgumentException e) {
            request.setAttribute(ERROR_MESSAGE, "Invalid data provided.");
        }

        try {
            List<CarDto> carList = carService.getCarByStatus(Status.AVAILABLE);
            request.setAttribute("cars", carList);
        } catch (DataAccessException e) {
            request.setAttribute(ERROR_MESSAGE, "An error occurred while retrieving the car list.");
        }

        getServletContext().getRequestDispatcher(MENU_RENT_CARS).forward(request, response);
    }
}
