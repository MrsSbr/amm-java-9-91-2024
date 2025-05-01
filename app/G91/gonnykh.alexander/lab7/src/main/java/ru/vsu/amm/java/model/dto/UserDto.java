package ru.vsu.amm.java.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vsu.amm.java.enums.Role;

@Data
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Role role;
}
