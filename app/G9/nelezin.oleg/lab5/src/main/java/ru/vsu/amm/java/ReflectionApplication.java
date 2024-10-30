package ru.vsu.amm.java;

public class ReflectionApplication {

    public static void main(String[] args) {
        Context context = new Context();
        SecondService secondService = context.getBean(SecondService.class);
        secondService.goodMethod();

        FirstService firstService = context.getBean(FirstService.class);
        firstService.badMethod();
    }
}