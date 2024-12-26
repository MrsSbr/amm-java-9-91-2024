package ru.vsu.amm.java.entity;

import java.util.Arrays;
import java.util.List;

public class MatchRecord {
    private String homeBestPlayer;
    private String awayBestPlayer;

    public MatchRecord(String homeBestPlayer,String awayBestPlayer){
        this.homeBestPlayer=homeBestPlayer;
        this.awayBestPlayer=awayBestPlayer;
    }

    public String getHomeBestPlayer() {
        return homeBestPlayer;
    }

    public void setHomeBestPlayer(String homeBestPlayer) {
        this.homeBestPlayer = homeBestPlayer;
    }

    public String getAwayBestPlayer() {
        return awayBestPlayer;
    }

    public void setAwayBestPlayer(String awayBestPlayer) {
        this.awayBestPlayer = awayBestPlayer;
    }

    public List<String> getPlayers() {
        return Arrays.asList(homeBestPlayer, awayBestPlayer);
    }
}