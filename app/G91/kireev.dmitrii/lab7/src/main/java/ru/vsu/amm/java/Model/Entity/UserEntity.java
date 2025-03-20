package ru.vsu.amm.java.Model.Entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserEntity {

    private long userId;
    private String name;
    private String email;
    private String password;
    private String phone;

}
