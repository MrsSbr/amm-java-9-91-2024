package ru.vsu.amm.java;

import ru.vsu.amm.java.Constans.Constants;
import ru.vsu.amm.java.Entity.Reply;
import ru.vsu.amm.java.Enums.CarBrand;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ReplyGenerator {
    public static final Random random = new Random();
    private static final List<CarBrand> brands = new ArrayList<>(Arrays.asList(CarBrand.values()));

    private ReplyGenerator() {
    }

    public static Reply Generator() {
        int age = random.nextInt(Constants.MAX_AGE - Constants.MIN_AGE + 1) + Constants.MIN_AGE;
        CarBrand car = brands.get(random.nextInt(brands.size()));
        return new Reply(car, age);
    }
}
