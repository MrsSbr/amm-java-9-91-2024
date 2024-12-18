package ru.vsu.amm.java;

import java.util.Objects;

interface WaterVessel extends Navigable {
    String getName();

    VesselType getType();

    double getLength();

    int getCrewSize();

    void displayInfo();

    String toString();

    boolean equals(Object o);

    int hashCode();

}
