package ru.vsu.amm.java;

import java.util.Objects;

public class Airplane extends MilitaryVehicle {
    private final int maxHeight;

    public int getMaxHeight() {
        return maxHeight;
    }

    public Airplane(String armament,
                    int capacity,
                    int speed,
                    int maxHeight) {
        super(armament, capacity, speed);
        this.maxHeight = maxHeight;
    }

    @Override
    public String toString() {
        return "[AIRPLANE]\n" +
                super.toString() +
                "\nMax Height:\t" + maxHeight;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && ((Airplane) obj).maxHeight == maxHeight;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + Objects.hash(maxHeight);
    }

}
