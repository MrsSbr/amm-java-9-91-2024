package ru.vsu.amm.java.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {
    private long id;
    private String nickname;
    private String password;
    private double rating;
    private List<Role> roles;

    public UserEntity(String nickname, String password, double rating, List<Role> roles) {
        this.nickname = nickname;
        this.password = password;
        this.rating = rating;
        this.roles = roles;
    }
}
