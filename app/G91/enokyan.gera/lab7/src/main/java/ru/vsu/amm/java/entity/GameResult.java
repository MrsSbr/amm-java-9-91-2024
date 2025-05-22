package ru.vsu.amm.java.entity;

import lombok.Getter;

@Getter
public enum GameResult {
    FirstPlayerWon(0d),
    Draw(0.5d),
    SecondPlayerWon(1d);

    private final double points;

    GameResult(double points) {
        this.points = points;
    }
}
