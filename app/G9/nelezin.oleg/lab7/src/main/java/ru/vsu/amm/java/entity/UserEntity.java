package ru.vsu.amm.java.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    private Long id;

    private String login;

    private String password;
}
