package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vsu.amm.java.enums.Role;

@Data
@AllArgsConstructor
public class UserEntity {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Role role;
}