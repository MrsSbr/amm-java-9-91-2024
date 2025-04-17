package ru.vsu.amm.java.service.implementations;

import ru.vsu.amm.java.dto.TripDto;
import ru.vsu.amm.java.dto.TripEndDto;
import ru.vsu.amm.java.entities.ScooterEntity;
import ru.vsu.amm.java.entities.TripEntity;
import ru.vsu.amm.java.enums.ScooterStatus;
import ru.vsu.amm.java.repository.ScooterRepository;
import ru.vsu.amm.java.repository.TripRepository;
import ru.vsu.amm.java.repository.UserRepository;
import ru.vsu.amm.java.service.interfaces.TripService;
import ru.vsu.amm.java.utils.ErrorMessages;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

public class TripServiceImpl implements TripService {
    private final ScooterRepository scooterRepository;
    private final TripRepository tripRepository;
    private final UserRepository userRepository;

    public TripServiceImpl() {
        this.scooterRepository = new ScooterRepository();
        this.tripRepository = new TripRepository();
        this.userRepository = new UserRepository();
    }

    @Override
    public void bookScooter(Long scooterId, String userLogin) {
        ScooterEntity scooterEntity = scooterRepository.findById(scooterId).
                orElseThrow(() -> new NoSuchElementException(ErrorMessages.SCOOTER_NOT_FOUND));

        if (!scooterEntity.getStatus().equals(ScooterStatus.FREE))
            throw new IllegalArgumentException(ErrorMessages.SCOOTER_AVAILABLE);

        scooterEntity.setStatus(ScooterStatus.BUSY);
        scooterRepository.update(scooterEntity);

        Long userId = userRepository.findByName(userLogin).get().getId();
        TripEntity tripEntity = new TripEntity(null, userId, scooterId, LocalDateTime.now(),
                null, scooterEntity.getLatitude(), scooterEntity.getLongitude(), null, null);
        tripRepository.save(tripEntity);
    }

    @Override
    public List<TripEndDto> getActiveTripsByUser(String userLogin) {
        return tripRepository.findAll().stream()
                .filter(trip -> (userRepository.findById(trip.getUserId()).get().getUserName().equals(userLogin))
                        && (trip.getEndTime() == null))
                .map(trip ->
                        new TripEndDto(
                                trip.getId(),
                                trip.getScooterId(),
                                scooterRepository.findById(trip.getScooterId()).get().getModel(),
                                trip.getStartTime()
                        )
                )
                .toList();
    }

    @Override
    public List<TripDto> getFinishTripsByUser(String userLogin) {
        return tripRepository.findAll().stream()
                .filter(trip -> (userRepository.findById(trip.getUserId()).get().getUserName().equals(userLogin))
                        && (trip.getEndTime() != null))
                .map(trip -> {
                    double distance = calculateDistance(
                            trip.getStartLatitude(),
                            trip.getStartLongitude(),
                            trip.getEndLatitude(),
                            trip.getEndLongitude()
                    );
                    return new TripDto(
                            trip.getId(),
                            trip.getScooterId(),
                            scooterRepository.findById(trip.getScooterId()).get().getModel(),
                            trip.getStartTime(),
                            trip.getEndTime(),
                            distance
                    );
                })
                .toList();
    }

    @Override
    public void finishTrip(Long tripId, double latitude, double longitude) {
        TripEntity tripEntity = tripRepository.findById(tripId)
                .orElseThrow(() -> new NoSuchElementException(ErrorMessages.TRIP_NOT_FOUND));
        if (tripEntity.getEndTime() != null) {
            throw new IllegalArgumentException("Поездка уже завершена");
        }

        tripEntity.setEndTime(LocalDateTime.now());
        tripEntity.setEndLatitude(latitude);
        tripEntity.setEndLongitude(longitude);

        tripRepository.update(tripEntity);

        ScooterEntity scooterEntity = scooterRepository.findById(tripEntity.getScooterId())
                .orElseThrow(() -> new NoSuchElementException(ErrorMessages.SCOOTER_NOT_FOUND));
        scooterEntity.setStatus(ScooterStatus.FREE);
        scooterEntity.setLatitude(latitude);
        scooterEntity.setLongitude(longitude);
        scooterRepository.update(scooterEntity);
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        final int EARTH_RADIUS_KM = 6371;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS_KM * c;
    }
}
