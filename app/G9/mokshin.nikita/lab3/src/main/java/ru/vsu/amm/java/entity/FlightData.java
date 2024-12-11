package ru.vsu.amm.java.entity;

import java.util.ArrayList;
import java.util.List;

public class FlightData {
    public static final int MAX_NUMBER_FLIGHT = 10;
    public static final int MIN_NUMBER_FLIGHT = 1;
    private final List<Family> families;

    public FlightData() {
        families = new ArrayList<>();
    }

    public void addFamily(Family family) {
        families.add(family);
    }

    public Family getFamily(int index) {
        return families.get(index);
    }

    public List<Family> getFamilies() {
        return families;
    }
}
