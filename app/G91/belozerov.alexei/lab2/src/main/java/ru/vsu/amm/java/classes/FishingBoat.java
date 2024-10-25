package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.enums.FishType;

import java.util.Objects;

public class FishingBoat extends Vessel {
    private final FishType fishType;

    public FishingBoat(String name, int capacity, FishType fishType) {
        super(name, capacity);
        this.fishType = fishType;
    }

    @Override
    public String getVesselType() {
        return "Рыболовное судно";
    }

    public void startFishing() {
        System.out.println(getName() + " начинает ловить рыбу типа " + fishType + ".");
    }

    @Override
    public String toString() {
        return "Название судна: " + getName()
                + "\nВместимость: " + getCapacity()
                + "\nТип рыбы: " + fishType.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FishingBoat vessel = (FishingBoat) o;
        return super.equals(vessel) && fishType == vessel.fishType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCapacity(), fishType);
    }
}
