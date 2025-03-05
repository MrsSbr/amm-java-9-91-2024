package ru.vsu.amm.java.Entity;

import lombok.Data;

@Data
public class UserEntity {

    private long userId;
    private String name;
    private String email;
    private String passwordHash;
    private String phone;


    public UserEntity (String name, String email, String passwordHash, String phone) {
    this.name = name;
    this.email = email;
    this.passwordHash = passwordHash;
    this.phone = phone;
    }
}
