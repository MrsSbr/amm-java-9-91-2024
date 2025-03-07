package ru.vsu.amm.java.entity;

import java.util.List;

public class Player {
    private long id;
    private String nickname;
    private String email;
    private String passwordHash;
    private double rating;
    private List<Role> roles;
    private List<Game> games;
}
