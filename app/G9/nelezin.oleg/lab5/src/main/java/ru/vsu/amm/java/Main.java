package ru.vsu.amm.java;

public class Main {

    public static void main(String[] args) {
        Context context = new Context();
        SecondService secondService = context.getBean(SecondService.class);
        secondService.goodMethod();
    }
}