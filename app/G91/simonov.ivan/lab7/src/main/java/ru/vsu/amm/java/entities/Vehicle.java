package ru.vsu.amm.java.entities;

public class Vehicle {

    private int vehicleId;
    private String registrationNumber;
    private String model;
    private String brand;
    private String colour;

    public Vehicle() {}

    public int getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(int vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    private boolean checkRegistrationNumber(String registrationNumber) {

        boolean isValid = true;

        String[] regNumData = registrationNumber.split(":");

        int letters = 0;
        int digits = 1;
        int regionCode = 2;
        int country = 3;

        if (regNumData.length != 4
                || !regNumData[letters].matches("[A-Z]+")
                || !regNumData[digits].matches("[0-9]+")
                || !regNumData[regionCode].matches("[0-9]+")
                || !regNumData[country].matches("[A-Z]+")) {

            isValid = false;
        }

        return isValid;
    }

    public void setRegistrationNumber(String registrationNumber) {
        if (checkRegistrationNumber(registrationNumber)) {
            this.registrationNumber = registrationNumber;
        } else {
            throw new IllegalArgumentException("Invalid registration number!");
        }
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }
}
