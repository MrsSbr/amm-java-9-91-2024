package ru.vsu.amm.java.enums;

public enum Player {
    KIRILL,
    STEPA,
    DIMA,
    SASHA,
    NIKITA,
    OLEG;

    public static Player getRandomPlayer() {
        Player[] players = values();
        return players[(int) (Math.random() * players.length)];
    }
}
