package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.vsu.amm.java.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private Long id;
    private String username;
    private String password;
    private String email;
    private Role role;
}