package ru.vsu.amm.java;

import java.util.Objects;

abstract class WaterVesselImpl implements WaterVessel {
    private String name;
    private VesselType type;
    private double length;

    public WaterVesselImpl(String name, VesselType type, double length) {
        this.name = name;
        this.type = type;
        this.length = length;
    }

    @Override
    public String getName() { return name; }
    @Override
    public VesselType getType() { return type; }
    @Override
    public double getLength() { return length; }

    @Override
    public String toString() {
        return "Name: " + name + "\nType: " + type + "\nLength: " + length + " meters";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if(obj == null || getClass() != obj.getClass()) return false;
        WaterVesselImpl that = (WaterVesselImpl) obj;
        return Double.compare(that.length, length) == 0 && type == that.type && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, type, length);
    }

    @Override
    public void displayInfo(){
        System.out.println(this);
    }

}
