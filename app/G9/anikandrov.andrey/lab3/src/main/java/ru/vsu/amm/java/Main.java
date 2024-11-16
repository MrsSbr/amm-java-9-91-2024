package ru.vsu.amm.java;

import java.util.Arrays;

public class Main {


    public static void main(String[] args) {
        var answers = new Answers(1500);
        var answersService = new AnswersService(answers);

        System.out.println(answersService.mostPopularBrand());
        System.out.println(answersService.uniqueBrands());
        System.out.println(Arrays.toString(answersService.brandByAge()));
    }

}