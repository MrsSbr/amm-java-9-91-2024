package ru.vsu.amm.java;

import java.util.Objects;

public class Spy extends MilitaryAircraft {
    private final int invisibility;
    private final Boolean unmanned;

    public Spy(String model, int maxSpeed, int invisibility, Boolean unmanned) {
        super(model, maxSpeed);
        this.invisibility = invisibility;
        this.unmanned = unmanned;
    }

    @Override
    public String toString() {
        return "FighterJet{" +
                "model='" + getModel() + '\'' +
                ", maxSpeed=" + getMaxSpeed() +
                ", unmanned=" + unmanned +
                ", invisibility=" + invisibility +
                '}';
    }

    @Override
    public String mission() {
        return getModel() + " reconnaissance!";
    }

    @Override
    public boolean equals(Object obj) {
        Spy that = (Spy) obj;
        if (obj == null)
            return false;
        else
            return invisibility == that.invisibility
                    && unmanned == that.unmanned
                    && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), invisibility, unmanned);
    }
}
