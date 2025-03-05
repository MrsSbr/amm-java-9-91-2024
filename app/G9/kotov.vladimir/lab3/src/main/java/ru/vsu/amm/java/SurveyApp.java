package ru.vsu.amm.java;

public class SurveyApp {
    public static void main(String[] args) {
        Survey survey = new Survey(SurveyConfig.TOTAL_STUDENTS);

        System.out.println("Number of students who found ALGEBRA useful: " +
                EvaluationService.countHelpfulEvaluations(Course.ALGEBRA, survey.getEvaluations()));

        System.out.println("Course(s) with the highest score: " +
                EvaluationService.coursesWithHighestScore(survey.getEvaluations()));

        System.out.println("Number of evaluations with no useful rating: " +
                EvaluationService.countNotHelpfulEvaluations(survey.getEvaluations()));
    }
}
