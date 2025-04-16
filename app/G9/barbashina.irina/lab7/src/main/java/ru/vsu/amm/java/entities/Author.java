package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Author {
    private Long id;
    private String surname;
    private String name;
    private String patronymic;
    private Date registrationDate;

    public void setRegistrationDate(Date registrationDate) {
        Date currentDate = new Date();
        if(registrationDate != null && registrationDate.after(currentDate)) {
            throw new IllegalArgumentException("Registration date cannot be in the future");
        }
        this.registrationDate = registrationDate;
    }
}
