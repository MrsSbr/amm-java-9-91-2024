package ru.vsu.amm.java;

import java.util.Objects;

class Tanker extends AbstractWaterVessel {
    private double oilCapacity;

    public Tanker(String name, double length, double oilCapacity) {
        super(name, VesselType.TANKER, length);
        this.oilCapacity = oilCapacity;
    }


    @Override
    public int getCrewSize() {
        return 30;
    }

    @Override
    public void navigateTo(String coordinates) {
        System.out.println(getName() + " is navigating to " + coordinates);
    }

    @Override
    public double getSpeed() {
        return 10.0;
    }

    @Override
    public String toString() {
        return super.toString() + "\nOil Capacity: " + oilCapacity + " barrels";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        Tanker tanker = (Tanker) obj;
        return Double.compare(tanker.oilCapacity, oilCapacity) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), oilCapacity);
    }

}
