package ru.vsu.amm.java.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class UserEntity {
    private long id;
    private String nickname;
    private String password;
    private double rating;
    private List<Role> roles;
}
