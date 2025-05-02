package ru.vsu.amm.java.models.requests;

import ru.vsu.amm.java.enums.Gender;

import java.time.LocalDate;

public record PsychologistRegisterRequest(String name,
                                          String surname,
                                          LocalDate birthdate,
                                          Gender gender,
                                          Short experience,
                                          String login,
                                          String password) {
}
