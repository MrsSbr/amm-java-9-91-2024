package ru.vsu.amm.java;

import java.util.Objects;

public class Tank extends MilitaryVehicle {
    private final int caliber;
    private final String armor;

    public int getCaliber() {
        return caliber;
    }

    public String getArmor() {
        return armor;
    }

    public Tank(String armament,
                int capacity,
                int speed,
                int caliber,
                String armor) {
        super(armament, capacity, speed);
        this.caliber = caliber;
        this.armor = armor;
    }

    @Override
    public String toString() {
        return "[TANK]\n"
                + super.toString()
                + "\nCaliber: " + caliber
                + "\nArmor: " + armor;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj)
                && caliber == ((Tank) obj).caliber
                && armor.equals(((Tank) obj).armor);
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(caliber, armor);
    }
}
