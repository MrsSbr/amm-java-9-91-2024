package ru.vsu.amm.java;

import java.util.Objects;

public abstract class Satellite implements ISatellite {

    private String name;

    private double width;

    private double length;

    private boolean isWorking;

    public Satellite(String name,
                     double width,
                     double length,
                     boolean isWorking) {
        this.name = name;
        this.width = width;
        this.length = length;
        this.isWorking = isWorking;
    }

    public Satellite() {}

    public String getName() {
        return name;
    }

    public void off() {
        if (!isWorking) {
            System.out.println("Спутник " + name + " уже выключен");
            return;
        }
        isWorking = false;
        System.out.println("Спутник " + name + " успешно выключен");
    }

    public void on() {
        if (isWorking) {
            System.out.println("Спутник " + name + " уже работает");
            return;
        }
        isWorking = true;
        System.out.println("Спутник " + name + " успешно включен");
    }

    @Override
    public String toString() {
        String result = "Название спутника: " + name +
                "\nШирина: " + width +
                "\nДлина: " + length +
                "\nРаботает: ";

        if (isWorking) {
            result += "Да";
        } else {
            result += "Нет";
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Satellite that = (Satellite) obj;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
