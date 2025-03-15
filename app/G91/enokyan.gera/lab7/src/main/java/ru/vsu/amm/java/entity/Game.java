package ru.vsu.amm.java.entity;

import java.time.LocalDateTime;

public class Game {
    private long id;
    private LocalDateTime finished;
    private long firstPlayerId;
    private Player firstPlayer;
    private long secondPlayerId;
    private Player secondPlayer;
    private double firstPlayersRating;
    private double secondPlayersRating;
    private short result;
}
