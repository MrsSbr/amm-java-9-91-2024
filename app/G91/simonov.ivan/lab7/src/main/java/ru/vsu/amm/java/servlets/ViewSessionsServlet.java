package ru.vsu.amm.java.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.SessionRepository;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.repository.VehicleRepository;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewSessions")
public class ViewSessionsServlet extends HttpServlet {

    private SessionRepository sessionRepository = new SessionRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        List<Session> sessionList =  sessionRepository.getAll();
        boolean isUser = false;

        switch (user.getRole()) {

            case ADMIN, EMPLOYEE -> {

                UserRepository userRepository = new UserRepository();
                VehicleRepository vehicleRepository = new VehicleRepository();

                sessionList.stream()
                        .filter(s -> s.getUser().getUserId() == user.getUserId())
                        .forEach(s -> {

                            s.setUser(userRepository.getById(s.getUser().getUserId()).get());
                            s.setVehicle(vehicleRepository.getById(s.getVehicle().getVehicleId()).get());

                        });

            }

            case USER -> {

                isUser = true;
                VehicleRepository vehicleRepository = new VehicleRepository();

                sessionList.stream()
                        .filter(s -> s.getUser().getUserId() == user.getUserId())
                        .forEach(s -> s.setVehicle(vehicleRepository.getById(s.getVehicle().getVehicleId()).get()));

            }

        }

        request.setAttribute("sessions", sessionList);
        request.setAttribute("isUser", isUser);
        request.getRequestDispatcher("userSessions.jsp").forward(request, response);

    }

}
