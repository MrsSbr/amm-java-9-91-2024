package ru.vsu.amm.java.entity;

import lombok.Getter;

@Getter
public enum Role {
    Player("Игрок"),
    Referee("Судья");

    private final String name;

    Role(String name) {
        this.name = name;
    }
}
