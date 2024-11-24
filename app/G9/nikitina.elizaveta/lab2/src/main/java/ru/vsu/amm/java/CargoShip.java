package ru.vsu.amm.java;

class CargoShip extends WaterVessel {
    private double cargoCapacity;

    public CargoShip(String vesselName, double vesselLength,  double cargoCapacity) {
        super(vesselName, "Cargo Ship", vesselLength);
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
}
