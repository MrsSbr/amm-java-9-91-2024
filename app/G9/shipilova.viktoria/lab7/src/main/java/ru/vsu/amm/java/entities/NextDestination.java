package ru.vsu.amm.java.entities;

import ru.vsu.amm.java.enams.NextDestinationName;

public class NextDestination {
    private long nextDestinationID;
    private NextDestinationName nextDestinationName;
    private String description;

    public NextDestination() {}

    public long getNextDestinationID() {
        return nextDestinationID;
    }

    public void setNextDestinationID(long nextDestinationID) {
        this.nextDestinationID = nextDestinationID;
    }

    public NextDestinationName getNextDestinationName() {
        return nextDestinationName;
    }

    public void setNextDestinationName(NextDestinationName nextDestinationName) {
        this.nextDestinationName = nextDestinationName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
