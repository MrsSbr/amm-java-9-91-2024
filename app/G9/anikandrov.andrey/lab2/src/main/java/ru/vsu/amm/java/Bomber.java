package ru.vsu.amm.java;

import java.util.Objects;

public class Bomber extends MilitaryAircraft {
    private final int bombLaying;

    public Bomber(String model, int maxSpeed, int bombLaying) {
        super(model, maxSpeed);
        this.bombLaying = bombLaying;
    }

    @Override
    public String toString() {
        return "Bomber{" +
                "model='" + getModel() + '\'' +
                ", maxSpeed=" + getMaxSpeed() +
                ", bombLaying=" + bombLaying +
                '}';
    }

    @Override
    public String mission() {
        return getModel() + " bombing!";
    }

    @Override
    public boolean equals(Object obj) {
        Bomber that = (Bomber) obj;
        if (obj == null)
            return false;
        else
            return bombLaying == that.bombLaying && super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), bombLaying);
    }
}

