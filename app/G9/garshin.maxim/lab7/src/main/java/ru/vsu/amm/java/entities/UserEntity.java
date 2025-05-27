package ru.vsu.amm.java.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {
    private long userId;
    private String username;
    private String email;
    private String userPassword;

    public UserEntity(String username, String email, String userPassword) {
        this.username = username;
        this.email = email;
        this.userPassword = userPassword;
    }
}