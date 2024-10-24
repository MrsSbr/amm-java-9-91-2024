package ru.vsu.amm.java;

import java.util.Objects;

public class CargoShip extends VesselImpl {
    private final CargoType cargoType;

    public CargoShip(String name, int capacity, CargoType cargoType) {
        super(name, capacity);
        this.cargoType = cargoType;
    }


    @Override
    public String getVesselType() {
        return "Грузовое судно";
    }

    public void loadCargo() {
        System.out.println("Загрузка груза типа " + cargoType + " на " + getName() + ".");
    }

    @Override
    public String toString() {
        return "Название судна: " + getName()
                + "\nВместимость: " + getCapacity()
                + "\nТип груза: " + cargoType.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CargoShip vessel = (CargoShip) o;
        return super.equals(vessel) && cargoType == vessel.cargoType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getCapacity(), cargoType);
    }
}
