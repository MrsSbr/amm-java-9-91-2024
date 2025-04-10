package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserEntity {
    private Long id;
    private String username;
    private String password;
    private String email;
}