package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.repository.SessionRepository;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.repository.VehicleRepository;

import java.util.List;

public class ViewService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public ViewService() {

        sessionRepository = new SessionRepository();
        userRepository = new UserRepository();
        vehicleRepository = new VehicleRepository();

    }

    public List<Session> viewSessions(User user) {

        List<Session> sessionList = sessionRepository.getAll();

        return switch (user.getRole()) {

            case ADMIN, EMPLOYEE -> {

                sessionList.forEach(s -> {

                    s.setUser(userRepository.getById(s.getUser().getUserId()).get());
                    s.setVehicle(vehicleRepository.getById(s.getVehicle().getVehicleId()).get());

                });

                yield sessionList;

            }

            case USER -> sessionList.stream()
                    .filter(s -> s.getUser().getUserId() == user.getUserId())
                    .map(s -> {
                        s.setVehicle(vehicleRepository.getById(s.getVehicle().getVehicleId()).get());
                        return s;
                    })
                    .toList();

        };

    }
}
