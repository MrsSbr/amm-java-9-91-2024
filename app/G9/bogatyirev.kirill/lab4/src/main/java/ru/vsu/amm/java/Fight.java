package ru.vsu.amm.java;

import java.time.LocalDate;
import java.util.Date;

public class Fight {
    private int tournamentNumber;
    private String firstFighterName;
    private String secondFighterName;
    private String winner;
    private boolean fatality;
    private LocalDate fightDate;

    public Fight(int tournamentNumber, String firstFighterName,
                 String secondFighterName, String winner, boolean fatality, LocalDate fightDate) {
        this.tournamentNumber = tournamentNumber;
        this.firstFighterName = firstFighterName;
        this.secondFighterName = secondFighterName;
        this.winner = winner;
        this.fatality = fatality;
        this.fightDate = fightDate;
    }

    @Override
    public String toString() {
        return "Fight{" +
                "tournamentNumber=" + tournamentNumber +
                ", firstFighterName='" + firstFighterName + '\'' +
                ", secondFighterName='" + secondFighterName + '\'' +
                ", winner='" + winner + '\'' +
                ", fatality=" + fatality + '\'' +
                ", Date =" + fightDate +
                '}';
    }

    public int getTournamentNumber() {
        return tournamentNumber;
    }

    public String getFirstFighterName() {
        return firstFighterName;
    }

    public String getSecondFighterName() {
        return secondFighterName;
    }

    public String getWinner() {
        return winner;
    }

    public boolean isFatality() {
        return fatality;
    }

    public LocalDate getFightDate() {
        return fightDate;
    }
}
