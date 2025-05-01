package ru.vsu.amm.java.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    private Long userId;
    private String name;
    private String email;
    private String password;
    private String phone;

}
