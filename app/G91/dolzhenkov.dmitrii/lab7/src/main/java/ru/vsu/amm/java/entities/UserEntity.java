package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vsu.amm.java.enums.UserRole;

@Data
@AllArgsConstructor
public class UserEntity {
    private Long id;
    private String userName;
    private String password;
    private UserRole userRole;
}
