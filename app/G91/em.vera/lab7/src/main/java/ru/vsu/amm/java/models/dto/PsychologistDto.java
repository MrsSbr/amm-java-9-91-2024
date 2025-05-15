package ru.vsu.amm.java.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.vsu.amm.java.enums.Gender;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class PsychologistDto {
    private Long idPsychologist;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private Gender gender;
    private Short experience;
    private String login;
}