package ru.vsu.amm.java;

import java.util.ArrayList;
import java.util.List;

public class AnswersService
{
    private List<Answer> answers;

    public AnswersService(Answers answers) {
        this.answers = answers.getAnswers();
    }

    public CarBrand mostPopularBrand() {
        int[] brandCount = new int[CarBrand.values().length];

        for (Answer answer : answers) {
            brandCount[answer.getCarBrand().ordinal()]++;
        }

        int maxIndex = 0;
        for (int i = 1; i < brandCount.length; i++) {
            if (brandCount[i] > brandCount[maxIndex]) {
                maxIndex = i;
            }
        }

        return CarBrand.values()[maxIndex];
    }



    public CarBrand[] brandByAge() {
        int[][] ageBrandCount = new int[Constants.MAX_AGE][CarBrand.values().length];

        for (Answer answer : answers) {
            int brandIndex = answer.getCarBrand().ordinal();
            int age = answer.getAge();
            ageBrandCount[age][brandIndex]++;
        }

        CarBrand[] popularBrands = new CarBrand[Constants.AGE_DIFF];

        for (int age = Constants.MIN_AGE; age < Constants.MAX_AGE; age++) {
            int maxIndex = 0;

            for (int j = 1; j < CarBrand.values().length; j++) {

                if (ageBrandCount[age][j] > ageBrandCount[age][maxIndex]) {
                    maxIndex = j;
                }

            }
            popularBrands[age - Constants.MIN_AGE] = CarBrand.values()[maxIndex];
        }

        return popularBrands;
    }

    public List<CarBrand> uniqueBrands() {
        int[] brandCount = new int[CarBrand.values().length];

        for (Answer answer : answers) {
            brandCount[answer.getCarBrand().ordinal()]++;
        }

        List<CarBrand> uniqueBrands = new ArrayList<>();

        for (int i = 0; i < brandCount.length; i++) {
            if (brandCount[i] == 1) {
                uniqueBrands.add(CarBrand.values()[i]);
            }
        }

        return uniqueBrands;
    }

//    public List<CarBrand> uniqueBrands() {
////        List<CarBrand> uniqueBrands = new ArrayList<>();
////
////        for (Answer answer : answers) {
////            CarBrand brand = answer.getCarBrand();
////            if (!uniqueBrands.contains(brand)) {
////                uniqueBrands.add(brand);
////            }
////        }
////
////        return uniqueBrands;
////    }


}
