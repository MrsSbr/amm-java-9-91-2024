package ru.vsu.amm.java.entities;

import ru.vsu.amm.java.enams.NextDestinationName;

public class NextDestination {
    private long id;
    private NextDestinationName nextDestinationName;
    private String description;

    public NextDestination() {}

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "NextDestination{" +
                "id=" + id +
                ", nextDestinationName=" + nextDestinationName +
                ", description='" + description + '\'' +
                '}';
    }
}
