package ru.vsu.amm.java.converters;

import ru.vsu.amm.java.entities.FlightEntity;
import ru.vsu.amm.java.models.dto.FlightDto;

public class FlightConverter {

    public static FlightDto toDto(FlightEntity flightEntity) {
        return new FlightDto(
                flightEntity.getFlightId(),
                flightEntity.getOrigin(),
                flightEntity.getDestination(),
                flightEntity.getDepartureTime(),
                flightEntity.getArrivalTime(),
                flightEntity.getAirplaneModel(),
                flightEntity.getCapacity(),
                flightEntity.getPrice()
        );
    }
}
