package ru.vsu.amm.java;

import java.util.Objects;

public abstract class Car {
    private String model;
    private String color;
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
        return
                "model '" + model + '\n' +
                "color " + color + '\n';
    }

    @Override
    public boolean equals(Object o) {
        boolean result = false;
        if (this == o)
            result = true;
        else if (o == null || getClass() != o.getClass())
            result = false;
        else {
            Car car = (Car) o;
            result = Objects.equals(model, car.model) && Objects.equals(color, car.color);
        }
        return result;
    }

    @Override
    public int hashCode() {
        return Objects.hash(model, color);
    }
}
