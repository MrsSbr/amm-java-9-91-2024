package ru.vsu.amm.java;

public record GameResult(Team homeTeam, Team awayTeam, int homeTeamGoals, int awayTeamGoals) {
    public int getHomeTeamPoints() {
        if (homeTeamGoals > awayTeamGoals) {
            return 3;
        }
        if (homeTeamGoals == awayTeamGoals) {
            return 1;
        }
        return 0;
    }

    public int getAwayTeamPoints() {
        if (awayTeamGoals > homeTeamGoals) {
            return 3;
        }
        if (awayTeamGoals == homeTeamGoals) {
            return 1;
        }
        return 0;
    }
}

