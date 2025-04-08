package ru.vsu.amm.java.requests;

public record AddVehicleRequest(String registrationNumber,
                                String model,
                                String brand,
                                String colour) {
}
