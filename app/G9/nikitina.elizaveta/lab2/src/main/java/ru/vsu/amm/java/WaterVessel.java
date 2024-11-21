package ru.vsu.amm.java;

import java.util.Objects;

abstract class WaterVessel {
    private String vesselName;
    private String vesselType;
    private double vesselLength;

    public WaterVessel(String vesselName, String vesselType, double vesselLength) {
        this.vesselName = vesselName;
        this.vesselType = vesselType;
        this.vesselLength = vesselLength;
    }

    public String getVesselName() {
        return vesselName;
    }

    public String getVesselType() {
        return vesselType;
    }

    public double getVesselLength() {
        return vesselLength;
    }

    @Override
    public String toString() {
        return "Name: " + vesselName + "\nType: " + vesselType + "\nLength: " + vesselLength + " meters";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WaterVessel that = (WaterVessel) o;
        return Double.compare(that.vesselLength, vesselLength) == 0 && vesselName.equals(that.vesselName) && vesselType.equals(that.vesselType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vesselName, vesselType, vesselLength);
    }

    public abstract int getCrewSize();
    void displayInfo(){
        System.out.println(this);
    }
}
