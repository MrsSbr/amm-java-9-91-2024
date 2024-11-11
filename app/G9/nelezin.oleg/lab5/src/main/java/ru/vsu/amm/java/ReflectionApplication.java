package ru.vsu.amm.java;

import ru.vsu.amm.java.annotation.service.impl.FirstServiceImpl;
import ru.vsu.amm.java.annotation.service.impl.SecondServiceImpl;

public class ReflectionApplication {

    public static void main(String[] args) {
        Context context = new Context();
        FirstServiceImpl firstService = context.getBean(FirstServiceImpl.class);
        firstService.goodMethod();

        SecondServiceImpl secondService = context.getBean(SecondServiceImpl.class);
        secondService.badMethod();
    }
}