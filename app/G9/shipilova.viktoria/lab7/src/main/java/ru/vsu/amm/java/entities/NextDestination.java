package ru.vsu.amm.java.entities;

public class NextDestination {
    private int nextDestinationID;
    private String nextDestinationName;
    private String description;

    public NextDestination() {}

    public int getNextDestinationID() {
        return nextDestinationID;
    }

    public void setNextDestinationID(int nextDestinationID) {
        this.nextDestinationID = nextDestinationID;
    }

    public String getNextDestinationName() {
        return nextDestinationName;
    }

    public void setNextDestinationName(String nextDestinationName) {
        this.nextDestinationName = nextDestinationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
