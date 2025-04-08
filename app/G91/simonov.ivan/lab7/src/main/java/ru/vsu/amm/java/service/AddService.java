package ru.vsu.amm.java.service;

import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.entities.User;
import ru.vsu.amm.java.entities.Vehicle;
import ru.vsu.amm.java.exceptions.AddException;
import ru.vsu.amm.java.repository.SessionRepository;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.repository.VehicleRepository;
import ru.vsu.amm.java.requests.AddSessionRequest;
import ru.vsu.amm.java.requests.AddVehicleRequest;

import java.time.LocalDateTime;
import java.util.Optional;

public class AddService {

    private final SessionRepository sessionRepository;
    private final UserRepository userRepository;
    private final VehicleRepository vehicleRepository;

    public AddService() {

        sessionRepository = new SessionRepository();
        userRepository = new UserRepository();
        vehicleRepository = new VehicleRepository();
    }

    public void addSession(AddSessionRequest request) {

        Session userSession = new Session();
        userSession.setEntryDate(LocalDateTime.now());
        userSession.setParkingPrice(request.parkingPrice());

        Optional<User> repUser = userRepository.getById(request.userId());

        if (repUser.isPresent()) {

            userSession.setUser(repUser.get());

        } else {

            throw new AddException("User not found!");

        }

        AddVehicleRequest addVehicleRequest = request.addVehicleRequest();
        Vehicle vehicle = new Vehicle();

        vehicle.setRegistrationNumber(addVehicleRequest.registrationNumber());
        vehicle.setModel(addVehicleRequest.model());
        vehicle.setBrand(addVehicleRequest.brand());
        vehicle.setColour(addVehicleRequest.colour());

        Optional<Vehicle> repVehicle = vehicleRepository.getByInfo(vehicle);

        if (repVehicle.isPresent()) {

            userSession.setVehicle(repVehicle.get());

        } else {

            int vehicleId = vehicleRepository.save(vehicle);
            vehicle.setVehicleId(vehicleId);
            userSession.setVehicle(vehicle);

        }

        sessionRepository.save(userSession);

    }

}
