package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.entities.BookingEntity;
import ru.vsu.amm.java.entities.ClientEntity;
import ru.vsu.amm.java.entities.FlightEntity;
import ru.vsu.amm.java.models.requests.ClientLoginRequest;
import ru.vsu.amm.java.models.requests.ClientRegisterRequest;

public class SetupHelper {

    public static ClientEntity getClientEntity() {
        return new ClientEntity(
                TestApplicationConstants.CLIENT_ID,
                TestApplicationConstants.CLIENT_NAME,
                TestApplicationConstants.CLIENT_EMAIL,
                TestApplicationConstants.CLIENT_PASSWORD
        );
    }

    public static FlightEntity getFlightEntity() {
        return new FlightEntity(
                TestApplicationConstants.FLIGHT_ID,
                TestApplicationConstants.ORIGIN,
                TestApplicationConstants.DESTINATION,
                TestApplicationConstants.DEPARTURE_TIME,
                TestApplicationConstants.ARRIVAL_TIME,
                TestApplicationConstants.AIRPLANE_MODEL,
                TestApplicationConstants.CAPACITY,
                TestApplicationConstants.PRICE
        );
    }

    public static BookingEntity getBookingEntity() {
        return new BookingEntity(
                TestApplicationConstants.BOOKING_ID,
                TestApplicationConstants.B_CLIENT_ID,
                TestApplicationConstants.B_FLIGHT_ID,
                TestApplicationConstants.SEAT_NUMBER,
                TestApplicationConstants.TICKET_NUMBER
        );
    }

    public static ClientRegisterRequest getClientRegisterRequest() {
        return new ClientRegisterRequest(
                TestApplicationConstants.CLIENT_NAME,
                TestApplicationConstants.CLIENT_EMAIL,
                TestApplicationConstants.CLIENT_PASSWORD
        );
    }

    public static ClientLoginRequest getClientLoginRequest() {
        return new ClientLoginRequest(
                TestApplicationConstants.CLIENT_EMAIL,
                TestApplicationConstants.CLIENT_ANOTHER_PASSWORD
        );
    }
}
