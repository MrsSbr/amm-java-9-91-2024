package ru.vsu.amm.java.util;

import ru.vsu.amm.java.enums.Player;
import ru.vsu.amm.java.entity.MatchRecord;


public class MatchRecordFactory {
    public static MatchRecord generateRecord() {
        String homeBestPlayer = Player.getRandomPlayer().name();
        String awayBestPlayer = Player.getRandomPlayer().name();
        return new MatchRecord(homeBestPlayer, awayBestPlayer);
    }

}

