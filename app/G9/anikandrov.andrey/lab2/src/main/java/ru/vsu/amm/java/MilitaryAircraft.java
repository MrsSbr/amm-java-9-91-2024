package ru.vsu.amm.java;

import java.util.Objects;

public abstract class MilitaryAircraft implements Aircraft{
    private final String model;
    private final int maxSpeed;

    public MilitaryAircraft(String model, int maxSpeed) {
        this.model = model;
        this.maxSpeed = maxSpeed;
    }

    @Override
    public void flySpeed() {
        System.out.println("<" + model + "> flies at " + maxSpeed + " km/h.");
    }

    public String getModel() {
        return model;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    @Override
    public String toString() {
        return "MilitaryAircraft{" +
                "model='" + model + '\'' +
                ", maxSpeed=" + maxSpeed +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        MilitaryAircraft that = (MilitaryAircraft) obj;
        if (obj == null)
            return false;
        else
            return model == that.model && maxSpeed == that.maxSpeed;
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxSpeed, model);
    }

    public String mission() {
        return model + " carries out orders!";
    }
}
