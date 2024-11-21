package ru.vsu.amm.java.Entity;

import ru.vsu.amm.java.Enums.CarBrand;

public class Reply {
    private int age;
    private CarBrand carBrand;

    public Reply() {
    };

    public Reply(CarBrand brand, int age) {
        this.age = age;
        this.carBrand = brand;
    }

    public int getAge() {
        return age;
    }

    public CarBrand getCarBrand() {
        return carBrand;
    }

    @Override
    public String toString() {
        return "Reply{" +
                "age=" + age +
                ", carBrand=" + getCarBrand() +
                "}";
    }
}
