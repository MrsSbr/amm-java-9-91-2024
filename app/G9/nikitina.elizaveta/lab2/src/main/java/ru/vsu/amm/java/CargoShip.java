package ru.vsu.amm.java;

import java.util.Objects;

class CargoShip extends WaterVesselImpl {
    private double cargoCapacity;

    public CargoShip(String vesselName, double vesselLength, double cargoCapacity) {
        super(vesselName, VesselType.CARGO_SHIP, vesselLength);
        this.cargoCapacity = cargoCapacity;
    }

    @Override
    public String toString() {
        return super.toString() + "\nCargo Capacity: " + cargoCapacity + " tons";
    }

    @Override
    public int getCrewSize() {
        return 20;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        if (!super.equals(obj)) return false;
        CargoShip cargoShip = (CargoShip) obj;
        return Double.compare(cargoShip.cargoCapacity, cargoCapacity) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), cargoCapacity);
    }

    @Override
    public void navigateTo(String coordinates) {
        System.out.println("Cargo ship " + getName() + " is navigating to: " + coordinates);
    }

    @Override
    public double getSpeed() {
        return 12.0; //Example speed
    }
}
