package ru.vsu.amm.java.utils;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TestApplicationConstants {

    public static final Long CLIENT_ID = 1L;
    public static final String CLIENT_EMAIL = "5@mail.com";
    public static final String CLIENT_NAME = "client";
    public static final String CLIENT_PASSWORD = "password";
    public static final String CLIENT_ANOTHER_PASSWORD = "secret123";

    public static final Long FLIGHT_ID = 1L;
    public static final String ORIGIN = "origin";
    public static final String DESTINATION = "destination";
    public static final LocalDateTime DEPARTURE_TIME = LocalDateTime.of(2020, 1, 1, 0, 0);
    public static final LocalDateTime ARRIVAL_TIME = LocalDateTime.of(2020, 1, 1, 1, 0);
    public static final String AIRPLANE_MODEL = "airplane";
    public static final Short CAPACITY = 100;
    public static final BigDecimal PRICE = BigDecimal.valueOf(100);

    public static final Long BOOKING_ID = 1L;
    public static final Long B_CLIENT_ID = 1L;
    public static final Long B_FLIGHT_ID = 1L;
    public static final String SEAT_NUMBER = "10D";
    public static final String TICKET_NUMBER = "JFJ483FD";

}
