package ru.vsu.amm.java;

import ru.vsu.amm.java.Entity.Answers;
import ru.vsu.amm.java.Service.AnswersService;

public class PopularCarTask {

    public static void main(String[] args) {
        var answers = new Answers(1500);
        var answersService = new AnswersService(answers);

        System.out.println(answersService.mostPopularBrand());
        System.out.println(answersService.uniqueBrands());
        System.out.println(answersService.brandByAge());
    }

}