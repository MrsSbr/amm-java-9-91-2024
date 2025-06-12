package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.enums.Gender;

import java.math.BigDecimal;
import java.time.LocalDate;

public class TestDataConstants {

    // Client
    public static final Long CLIENT_ID = 1L;
    public static final String CLIENT_EMAIL = "client@mail.com";
    public static final String CLIENT_NAME = "Test Client";
    public static final String CLIENT_PASSWORD = "hashedPassword";

    // Psychologist
    public static final Long PSYCHOLOGIST_ID = 1L;
    public static final String PSYCHOLOGIST_LOGIN = "psychologist";
    public static final String PSYCHOLOGIST_PASSWORD = "hashedPassword";
    public static final String PSYCHOLOGIST_NAME = "Test Psychologist";
    public static final String PSYCHOLOGIST_SURNAME = "Test Psychologist";
    public static final short PSYCHOLOGIST_EXPERIENCE = 10;
    public static final Gender PSYCHOLOGIST_GENDER = Gender.FEMALE;
    public static final LocalDate PSYCHOLOGIST_BIRTHDATE = LocalDate.of(1990, 1, 1);

    // Session
    public static final Long SESSION_ID = 1L;
    public static final LocalDate SESSION_DATE = LocalDate.of(1990, 1, 1);
    public static final BigDecimal SESSION_PRICE = BigDecimal.valueOf(100);
    public static final short SESSION_DURATION = 10;
}
