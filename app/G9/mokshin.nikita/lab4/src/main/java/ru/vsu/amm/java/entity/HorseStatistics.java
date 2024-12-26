package ru.vsu.amm.java.entity;

public class HorseStatistics {
    private int totalRaces;
    private int firstPlaces;
    private int secondPlaces;
    private int thirdPlaces;

    public void addFirstPlace() {
        firstPlaces++;
        totalRaces++;
    }

    public void addSecondPlace() {
        secondPlaces++;
        totalRaces++;
    }

    public void addThirdPlace() {
        thirdPlaces++;
        totalRaces++;
    }

    @Override
    public String toString() {
        return "Participation: " + totalRaces +
                ", 1 places: " + firstPlaces +
                ", 2 places: " + secondPlaces +
                ", 3 places: " + thirdPlaces;
    }

    public int getTotalRaces() {
        return totalRaces;
    }

    public int getFirstPlaces() {
        return firstPlaces;
    }

    public int getSecondPlaces() {
        return secondPlaces;
    }

    public int getThirdPlaces() {
        return thirdPlaces;
    }
}
