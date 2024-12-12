package ru.vsu.amm.java;

import java.util.Objects;

public abstract class HardwareComponent {
    private final String manufacturer;
    private final String model;
    private final double price;

    public HardwareComponent(String manufacturer, String model, double price) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.price = price;
    }

    public String getName() {
        return manufacturer + " " + model;
    }

    public abstract String getDescription();

    public double getPrice() {
        return price;
    }

    public String getManufacturer (){ return manufacturer; }

    public String getModel (){ return model; }

    @Override
    public String toString() {
        return "Manufacturer: " + manufacturer + ", Model: " + model + ", Price: $" + price;}

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        HardwareComponent that = (HardwareComponent) obj;
        return Objects.equals(manufacturer, that.manufacturer) &&
                Objects.equals(model, that.model) &&
                Double.compare(price, that.price) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(manufacturer, model, price);
    }
}
