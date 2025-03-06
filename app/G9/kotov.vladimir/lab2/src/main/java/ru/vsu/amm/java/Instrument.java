package ru.vsu.amm.java;

import java.util.Objects;

public abstract class Instrument implements Playable {
    private String model;
    private double weight;
    private Material material;

    public Instrument(String model, double weight, Material material) {
        this.model = model;
        this.weight = weight;
        this.material = material;
    }

    public String getModel() {
        return model;
    }

    public double getWeight() {
        return weight;
    }

    public Material getMaterial() {
        return material;
    }

    // Публичный метод, который может быть переопределён наследниками
    public void tune() {
        System.out.println(model + " is being tuned.");
    }

    @Override
    public String toString() {
        return "Instrument Model: " + model +
                "\nWeight: " + weight +
                "\nMaterial: " + material;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Instrument)) return false;
        Instrument that = (Instrument) o;
        return Double.compare(that.weight, weight) == 0 &&
                model.equals(that.model) &&
                material == that.material;
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, weight, material);
    }
}
