package ru.vsu.amm.java.Entities;

import lombok.Data;

@Data
public class Smartphone {
    private Long smartphoneId;
    private String brand;
    private String model;
    private int ram;
    private int storageMemory;
    private float mainCameraResolution;
    private float screenSize;
    private String color;
    private float price;
    private int amount;

    public Smartphone() {
    }
}
