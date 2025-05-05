package ru.vsu.amm.java.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class GameEntity {
    private long id;
    private long firstPlayersId;
    private long secondPlayersId;
    private LocalDateTime finished;
    private GameResult result;
    private double firstPlayersRatingBefore;
    private double secondPlayersRatingBefore;
    private double firstPlayersRatingChange;
    private double secondPlayersRatingChange;
}
