package ru.vsu.amm.java.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class GameEntity {
    private long id;
    private long firstPlayerId;
    private long secondPlayerId;
    private LocalDateTime finished;
    private GameResult result;
    private double firstPlayersRating;
    private double secondPlayersRating;
}
