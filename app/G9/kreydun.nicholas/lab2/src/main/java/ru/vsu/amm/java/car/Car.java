package ru.vsu.amm.java.car;

import java.util.Objects;

public abstract class Car {
    private final String model;
    private final String color;
    public Car(String model, String color) {
        this.model = model;
        this.color = color;
    }
    public String getModel() {
        return model;
    }
    public String getColor() {
        return color;
    }
    abstract void beep();
    @Override
    public String toString() {
        return "model '" + model + '\n' + "color " + color + '\n';
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        else if (o == null || getClass() != o.getClass())
            return false;
        else {
            Car car = (Car) o;
            return Objects.equals(model, car.model) && Objects.equals(color, car.color);
        }
    }
    @Override
    public int hashCode() {
        return Objects.hash(model, color);
    }
}
