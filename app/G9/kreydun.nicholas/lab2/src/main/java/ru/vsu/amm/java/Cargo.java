package ru.vsu.amm.java;

import java.util.Objects;

public class Cargo {
    private final String cargoName;
    private final int cargoPrice;
    private final int cargoWeight;
    public Cargo(String cargoName, int cargoPrice, int cargoWeight) {
        this.cargoName = cargoName;
        this.cargoPrice = cargoPrice;
        this.cargoWeight = cargoWeight;
    }
    public String getCargoName() {
        return cargoName;
    }
    public int getCargoPrice() {
        return cargoPrice;
    }
    public int getCargoWeight() {
        return cargoWeight;
    }

    @Override
    public String toString() {
        return "cargoName'" + cargoName +
                "\ncargoPrice " + cargoPrice +
                "\ncargoWeight " + cargoWeight;
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (this == o)
            result = true;
        else if (!(o instanceof Cargo))
            result = false;
        else{
            Cargo cargo = (Cargo) o;
            result = cargoPrice == cargo.cargoPrice && cargoWeight == cargo.cargoWeight && Objects.equals(cargoName, cargo.cargoName);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cargoName, cargoPrice, cargoWeight);
    }
}
