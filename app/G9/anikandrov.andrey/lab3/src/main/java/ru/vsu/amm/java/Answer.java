package ru.vsu.amm.java;

import java.util.Random;

public class Answer {
    private int age;
    private CarBrand carBrand;

    public Answer(int age) {
        this.age = age;
        this.carBrand = getRandomCarBrand();
    }

    public Answer(CarBrand car, int age) {
        this.age = age;
        this.carBrand = car;
    }

    public CarBrand getRandomCarBrand() {
        CarBrand[] brands = CarBrand.values();
        Random random = new Random();
        return brands[random.nextInt(brands.length)];
    }

    public int getAge() {
        return age;
    }

    public CarBrand getCarBrand() {
        return carBrand;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "age=" + age +
                ", carBrand=" + getCarBrand() +
                "}";
    }


}
