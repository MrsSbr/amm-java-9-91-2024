package ru.vsu.amm.java;

public class Match {

    private final String homeBestPlayer;
    private final String awayBestPlayer;

    public Match(String homeBestPlayer, String awayBestPlayer) {
        this.homeBestPlayer = homeBestPlayer;
        this.awayBestPlayer = awayBestPlayer;
    }

    public String getHomeBestPlayer() {
        return homeBestPlayer;
    }

    public String getAwayBestPlayer() {
        return awayBestPlayer;
    }
}

