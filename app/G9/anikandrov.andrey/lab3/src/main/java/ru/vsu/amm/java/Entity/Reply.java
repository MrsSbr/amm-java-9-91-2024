package ru.vsu.amm.java.Entity;

import ru.vsu.amm.java.Enums.CarBrand;

import java.util.Random;

public class Reply {
    private int age;
    private CarBrand carBrand;

    public Reply() {
    };

    public Reply(CarBrand brand, int age) {
        this.age = age;
        this.carBrand = brand;
    }

//    public CarBrand getRandomCarBrand() {
//        CarBrand[] brands = CarBrand.values();
//        Random random = new Random();
//        return brands[random.nextInt(brands.length)];
//    }

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
