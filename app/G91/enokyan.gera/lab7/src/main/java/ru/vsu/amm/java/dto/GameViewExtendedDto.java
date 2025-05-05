package ru.vsu.amm.java.dto;

import java.util.Date;

public record GameViewExtendedDto(
        long id,
        String firstPlayersNickname,
        String secondPlayersNickname,
        Date finished,
        double result,
        double firstPlayersRatingBefore,
        double secondPlayersRatingBefore,
        double firstPlayersRatingChange,
        double secondPlayersRatingChange) {}
