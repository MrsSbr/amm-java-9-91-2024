package ru.vsu.amm.java.servlet;

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
import java.util.NoSuchElementException;

@WebServlet(name = "BookCarServlet", urlPatterns = "/bookCar")
public class BookCarServlet extends HttpServlet {
    private final CarService carService = new CarService();
    private final UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            Long carId = Long.parseLong(request.getParameter("carId"));
            String email = (String) session.getAttribute("email");
            Long customerId = userService.getUserByEmail(email).getId();
            CarDto carDto = carService.getCarById(carId);
            carService.bookCar(customerId, carDto);
            response.setStatus(HttpServletResponse.SC_OK);
        } catch (DataAccessException e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (NoSuchElementException e) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

}
