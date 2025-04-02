package ru.vsu.amm.java.util;

import ru.vsu.amm.java.entity.GameResult;

public class GameResultMapper {
    public static String mapGameResultToString(GameResult result) {
        return result.name();
    }

    public static GameResult mapStringToGameResult(String result) {
        return GameResult.valueOf(result);
    }
}
