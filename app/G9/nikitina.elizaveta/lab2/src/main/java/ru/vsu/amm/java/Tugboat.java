package ru.vsu.amm.java;

import java.util.Objects;

class Tugboat extends WaterVesselImpl {
    private int horsePower;

    public Tugboat(String name, double length, int horsePower) {
        super(name, VesselType.TUGBOAT, length);
        this.horsePower = horsePower;
    }

    @Override
    public String toString() {
        return super.toString() + "\nHorsepower: " + horsePower;
    }

    @Override
    public int getCrewSize() {
        return 5;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Tugboat tugboat = (Tugboat) obj;
        return horsePower == tugboat.horsePower;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), horsePower);
    }

    @Override
    public void navigateTo(String coordinates) {
        System.out.println(getName() + " is navigating to " + coordinates);
    }

    @Override
    public double getSpeed() {
        return 10.0;
    }
}