package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.vsu.amm.java.enums.Gender;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Psychologist {
    private Long idPsychologist;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private Gender gender;
    private Short experience;
    private String login;
    private String password;
}
