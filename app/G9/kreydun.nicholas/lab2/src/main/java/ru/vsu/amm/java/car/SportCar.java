package ru.vsu.amm.java.car;

import java.util.Objects;

public class SportCar extends PassengerCar {
    private final int capacityGasoline;
    private final String description;
    public SportCar(String model, String color, int year, int speed, int capacityGasoline, String description) {
        super(model, color, year, speed);
        this.capacityGasoline = capacityGasoline;
        this.description = description;
    }

    public int getCapacityGasoline() {
        return capacityGasoline;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return super.toString() + "\ncapacity пasoline: " + capacityGasoline + "\ndescription: " + description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        else if (o == null || getClass() != o.getClass())
            return false;
        else if (!super.equals(o))
            return false;
        else {
            SportCar sportCar = (SportCar) o;
            return capacityGasoline == sportCar.capacityGasoline && Objects.equals(description, sportCar.description);
        }
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), capacityGasoline, description);
    }

    @Override
    public void beep() {
        System.out.println("Виу виу виу виу!!!!!!");
    }
}
