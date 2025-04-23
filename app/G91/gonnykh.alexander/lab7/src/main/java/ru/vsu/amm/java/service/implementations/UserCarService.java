package ru.vsu.amm.java.service.implementations;

import ru.vsu.amm.java.model.dto.TripDto;
import ru.vsu.amm.java.repository.implementations.UserCarRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;

public class UserCarService {
    private final UserCarRepository userCarRepository;

    public UserCarService() {
        this.userCarRepository = new UserCarRepository();
    }


    public List<TripDto> getCurrentTripsByCustomerId(Long customerId) {
        return userCarRepository.findActiveTripsWithCarByUser(customerId);
    }

    public BigDecimal finishTrip(Long tripId) {
        userCarRepository.finishTrip(tripId);
        return userCarRepository.findById(tripId)
                .orElseThrow(() -> new NoSuchElementException("Trip not found"))
                .getFinalPrice();
    }
}
