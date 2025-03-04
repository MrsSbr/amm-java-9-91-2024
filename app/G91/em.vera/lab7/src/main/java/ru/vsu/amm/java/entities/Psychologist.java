package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Psychologist {
    private Long idPsychologist;
    private String name;
    private String surname;
    private LocalDate birthdate;
    private Character gender;
    private Short experience;
    private String login;
    private String password;
}
