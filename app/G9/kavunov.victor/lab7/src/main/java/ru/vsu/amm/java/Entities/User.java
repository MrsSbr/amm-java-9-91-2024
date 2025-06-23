package ru.vsu.amm.java.Entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@AllArgsConstructor
@Data
public class User {
    private Long userId;
    private String surname;
    private String name;
    private String patronymicname;
    private String phoneNumber;
    private String password;
    private String email;

    public User() {
    }
}
