package ru.vsu.amm.java.entities;

public class Urgency {

    private String urgency;
    private String urgencyColor;

    public Urgency() {};

    private String getUrgency() {
        return urgency;
    }

    private void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    private String getUrgencyColor() {
        return urgencyColor;
    }

    private void setUrgencyColor(String urgencyColor) {
        this.urgencyColor = urgencyColor;
    }
}
