package ru.vsu.amm.java.utils;

import ru.vsu.amm.java.entities.Client;
import ru.vsu.amm.java.entities.Psychologist;
import ru.vsu.amm.java.entities.Session;
import ru.vsu.amm.java.enums.Gender;
import ru.vsu.amm.java.models.requests.ClientLoginRequest;
import ru.vsu.amm.java.models.requests.ClientRegisterRequest;
import ru.vsu.amm.java.models.requests.PsychologistLoginRequest;
import ru.vsu.amm.java.models.requests.PsychologistRegisterRequest;

import java.time.LocalDate;

public class TestDataFactory {

    public static Client clientEntity() {
        return Client.builder()
                .idClient(TestDataConstants.CLIENT_ID)
                .email(TestDataConstants.CLIENT_EMAIL)
                .password(TestDataConstants.CLIENT_PASSWORD)
                .name(TestDataConstants.CLIENT_NAME)
                .build();
    }

    public static Psychologist psychologistEntity() {
        return Psychologist.builder()
                .idPsychologist(TestDataConstants.PSYCHOLOGIST_ID)
                .login(TestDataConstants.PSYCHOLOGIST_LOGIN)
                .password(TestDataConstants.PSYCHOLOGIST_PASSWORD)
                .name(TestDataConstants.PSYCHOLOGIST_NAME)
                .surname(TestDataConstants.PSYCHOLOGIST_SURNAME)
                .experience(TestDataConstants.PSYCHOLOGIST_EXPERIENCE)
                .gender(TestDataConstants.PSYCHOLOGIST_GENDER)
                .birthdate(TestDataConstants.PSYCHOLOGIST_BIRTHDATE)
                .build();
    }

    public static Session sessionEntity() {
        return Session.builder()
                .idSession(TestDataConstants.SESSION_ID)
                .idPsychologist(TestDataConstants.PSYCHOLOGIST_ID)
                .idClient(TestDataConstants.CLIENT_ID)
                .date(TestDataConstants.SESSION_DATE)
                .price(TestDataConstants.SESSION_PRICE)
                .duration(TestDataConstants.SESSION_DURATION)
                .build();
    }

    public static ClientRegisterRequest validClientRegisterRequest() {
        return new ClientRegisterRequest(
                TestDataConstants.CLIENT_NAME,
                TestDataConstants.CLIENT_EMAIL,
                TestDataConstants.CLIENT_PASSWORD);
    }

    public static ClientLoginRequest validClientLoginRequest() {
        return new ClientLoginRequest(
                "client@mail.com",
                "securePassword"
        );
    }

    public static PsychologistRegisterRequest validPsychologistRegisterRequest() {
        return new PsychologistRegisterRequest(
                TestDataConstants.PSYCHOLOGIST_NAME,
                TestDataConstants.PSYCHOLOGIST_SURNAME,
                TestDataConstants.PSYCHOLOGIST_BIRTHDATE,
                TestDataConstants.PSYCHOLOGIST_GENDER,
                TestDataConstants.PSYCHOLOGIST_EXPERIENCE,
                TestDataConstants.PSYCHOLOGIST_LOGIN,
                TestDataConstants.PSYCHOLOGIST_PASSWORD
        );
    }

    public static PsychologistLoginRequest validPsychologistLoginRequest() {
        return new PsychologistLoginRequest(
                "psychologist",
                "securePassword"
        );
    }
}
