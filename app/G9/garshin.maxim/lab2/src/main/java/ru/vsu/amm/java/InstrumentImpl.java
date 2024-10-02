package ru.vsu.amm.java;

import java.util.Objects;

public abstract class InstrumentImpl implements Instrument {
    private String name;
    private String brand;
    private double width;
    private double length;
    private boolean isReadyToWork;

    public InstrumentImpl() {}

    public InstrumentImpl(String name, double width, double length, String brand, boolean isReadyToWork) {
        this.name = name;
        this.width = width;
        this.length = length;
        this.brand = brand;
        this.isReadyToWork = isReadyToWork;
    }

    public String getName() {
        return name;
    }

    public String getBrand() {
        return brand;
    }

    public double getWidth() {
        return width;
    }

    public double getLength() {
        return length;
    }

    public void prepare() {
        if (isReadyToWork) {
            System.out.println("Инструмент " + name + " уже готов к работе");
            return;
        }
        isReadyToWork = true;
        System.out.println("Теперь инструмент " + name + " подготовлен к работе");
    }

    public void remove() {
        if (!isReadyToWork) {
            System.out.println("Инструмент " + name + " уже убран");
            return;
        }
        isReadyToWork = false;
        System.out.println("Теперь инструмент " + name + " приведён в первоначальное положение");
    }

    @Override
    public String toString() {
        return  "Название:" + name +
                "\nбренд: '" + brand + "'" +
                "\nширина: " + width +
                "\nдлина: " + length +
                "\nготов к работе: " + isReadyToWork;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InstrumentImpl that = (InstrumentImpl) o;
        return Double.compare(width, that.width) == 0 &&
                Double.compare(length, that.length) == 0 &&
                Objects.equals(name, that.name) &&
                Objects.equals(brand, that.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, brand, width, length, isReadyToWork);
    }
}