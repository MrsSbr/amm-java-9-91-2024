package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Author {
    private long id;
    private String surname;
    private String name;
    private String patronymic;
    private java.sql.Date registrationDate;
}
