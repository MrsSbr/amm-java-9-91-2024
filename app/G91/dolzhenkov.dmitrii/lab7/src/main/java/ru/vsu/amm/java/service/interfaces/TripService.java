package ru.vsu.amm.java.service.interfaces;

import ru.vsu.amm.java.dto.TripDto;
import ru.vsu.amm.java.dto.TripEndDto;

import java.util.List;

public interface TripService {
    void bookScooter(Long scooterId, String userLogin);
    List<TripEndDto> getActiveTripsByUser(String userLogin);
    void finishTrip(Long tripId, double latitude, double longitude);
    List<TripDto> getFinishTripsByUser(String userLogin);
}
