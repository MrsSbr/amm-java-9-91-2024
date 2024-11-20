package ru.vsu.amm.java.Service;

import ru.vsu.amm.java.Enums.CarBrand;
import ru.vsu.amm.java.Constans.Constants;
import ru.vsu.amm.java.Entity.Answers;
import ru.vsu.amm.java.Entity.Reply;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
;

public class AnswersService {
    private List<Reply> answers;

    public AnswersService(Answers answers) {
        this.answers = answers.getAnswers();
    }

    public CarBrand mostPopularBrand() {;
        List<Integer> brandCount = new ArrayList<>(Collections.nCopies(CarBrand.values().length, 0));

        for (Reply reply : answers) {
            int brandIndex = reply.getCarBrand().ordinal();
            brandCount.set(brandIndex, brandCount.get(brandIndex) + 1);
        }

        int maxIndex = 0;
        for (int i = 1; i < brandCount.size(); i++) {
            if (brandCount.get(i) > brandCount.get(maxIndex)) {
                maxIndex = i;
            }
        }

        return CarBrand.values()[maxIndex];
    }


    public List<CarBrand> brandByAge() {
        List<List<Integer>> ageBrandCount = new ArrayList<>();

        for (int i = 0; i < Constants.MAX_AGE; i++) {
            ageBrandCount.add(new
                    ArrayList<>(Collections.nCopies(CarBrand.values().length, 0)));
        }

        for (Reply reply : answers) {
            int brandIndex = reply.getCarBrand().ordinal();
            int age = reply.getAge();
            ageBrandCount.get(age).set(brandIndex,
                    ageBrandCount.get(age).get(brandIndex) + 1);
        }

        List<CarBrand> popularBrands = new ArrayList<>();

        for (int age = Constants.MIN_AGE; age < Constants.MAX_AGE; age++) {
            int maxIndex = 0;

            for (int j = 1; j < CarBrand.values().length; j++) {
                if (ageBrandCount.get(age).get(j)
                        > ageBrandCount.get(age).get(maxIndex)) {
                    maxIndex = j;
                }
            }
            popularBrands.add(CarBrand.values()[maxIndex]);
        }

        return popularBrands;
    }


    public List<CarBrand> uniqueBrands() {
        List<Integer> brandCount = new
                ArrayList<>(Collections.nCopies(CarBrand.values().length, 0));

        for (Reply reply : answers) {
            int brandIndex = reply.getCarBrand().ordinal();
            brandCount.set(brandIndex, brandCount.get(brandIndex) + 1);
        }

        List<CarBrand> uniqueBrands = new ArrayList<>();

        for (int i = 0; i < brandCount.size(); i++) {
            if (brandCount.get(i) == 1) {
                uniqueBrands.add(CarBrand.values()[i]);
            }
        }

        return uniqueBrands;
    }
}
