package ru.vsu.amm.java.Entities;

public class Smartphone {
    private int smartphoneId;
    private String brand;
    private String model;
    private int ram;
    private int storageMemory;
    private float mainCameraResolution;
    private float screenSize;
    private String color;
    private float price;
    private int amount;

    public Smartphone() {}

    public Smartphone(int smartphoneId, String brand, String model, int ram, int storageMemory,
                      float mainCameraResolution, float screenSize, String color, float price, int amount) {
        this.smartphoneId = smartphoneId;
        this.brand = brand;
        this.model = model;
        this.ram = ram;
        this.storageMemory = storageMemory;
        this.mainCameraResolution = mainCameraResolution;
        this.screenSize = screenSize;
        this.color = color;
        this.price = price;
        this.amount = amount;
    }

    public long getSmartphoneId() { return this.smartphoneId; }
    public void setSmartphoneId(int id) { this.smartphoneId = id; }

    public String getBrand() { return this.brand; }
    public void setBrand(String brand) { this.brand = brand; }

    public String getModel() { return this.model; }
    public void setModel(String model) { this.model = model; }

    public int getRam() { return this.ram; }
    public void setRam(int ram) { this.ram = ram; }

    public int getStorageMemory() { return this.storageMemory; }
    public void setStorageMemory(int storageMemory) { this.storageMemory = storageMemory; }

    public float getMainCameraResolution() { return this.mainCameraResolution; }
    public void setMainCameraResolution(float mainCameraResolution) { this.mainCameraResolution = mainCameraResolution; }

    public float getScreenSize() { return this.screenSize; }
    public void setScreenSize(float screenSize) { this.screenSize = screenSize; }

    public String getColor() { return this.color; }
    public void setColor(String color) { this.color = color; }

    public float getPrice() { return this.price; }
    public void setPrice(float price) { this.price = price; }

    public int getAmount() { return this.amount; }
    public void setAmount(int amount) { this.amount = amount; }
}
