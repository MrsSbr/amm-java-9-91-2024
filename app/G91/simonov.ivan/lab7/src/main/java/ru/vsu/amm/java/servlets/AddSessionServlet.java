package ru.vsu.amm.java.servlets;

import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.entities.Vehicle;
import ru.vsu.amm.java.repository.SessionRepository;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.repository.VehicleRepository;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@WebServlet("/addSession")
public class AddSessionServlet extends HttpServlet {

    private SessionRepository sessionRepository = new SessionRepository();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Session userSession = new Session();
        userSession.setEntryDate(LocalDateTime.now());
        userSession.setParkingPrice(new BigDecimal(request.getParameter("parkingPrice")));

        UserRepository userRepository = new UserRepository();
        Optional<User> repUser = userRepository.getById(Integer.parseInt(request.getParameter("userId")));

        if (repUser.isPresent()) {

            userSession.setUser(repUser.get());

        } else {

            response.sendRedirect("addSession.jsp?error=User not found!");

        }

        Vehicle vehicle = new Vehicle();
        vehicle.setRegistrationNumber(request.getParameter("registrationNumber"));
        vehicle.setModel(request.getParameter("model"));
        vehicle.setBrand(request.getParameter("brand"));
        vehicle.setColour(request.getParameter("colour"));

        VehicleRepository vehicleRepository = new VehicleRepository();
        Optional<Vehicle> repVehicle = vehicleRepository.getByInfo(vehicle);

        if (repVehicle.isPresent()) {

            userSession.setVehicle(repVehicle.get());

        } else {

            vehicle.setVehicleId(vehicleRepository.save(vehicle));
            userSession.setVehicle(vehicle);

        }

        sessionRepository.save(userSession);

        response.sendRedirect("addSession.jsp?message=Session successfully added!");
    }
}
