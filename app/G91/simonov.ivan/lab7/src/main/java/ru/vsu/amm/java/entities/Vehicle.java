package ru.vsu.amm.java.entities;

public class Vehicle {

    private int vehicleId;
    private String registrationNumber;
    private String model;
    private String brand;
    private String colour;

    public Vehicle() {}

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    private boolean checkRegistrationNumber(String registrationNumber) {

        boolean isValid = true;

        String[] splits = registrationNumber.split(":");

        int letters = 0;
        int digits = 1;
        int regionCode = 2;
        int country = 3;

        if (splits.length != 4 ||
                !splits[letters].matches("[A-Z]+") ||
                !splits[digits].matches("[0-9]+") ||
                !splits[regionCode].matches("[0-9]+") ||
                !splits[country].matches("[A-Z]+")) {

            throw new IllegalArgumentException("Invalid registration number!");
        }

        return isValid;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }
}
