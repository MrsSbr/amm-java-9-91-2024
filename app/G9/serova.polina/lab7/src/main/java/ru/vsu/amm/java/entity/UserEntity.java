package ru.vsu.amm.java.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity {
    private long id;
    private String name;
    private String surname;
    private String patronymic;
    private LocalDate birthday;
    private String email;
    private String passwordHash;
    private Role role;
}
