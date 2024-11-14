package ru.vsu.amm.java;

import ru.vsu.amm.java.configuration.Context;
import ru.vsu.amm.java.service.impl.FirstServiceImpl;
import ru.vsu.amm.java.service.impl.SecondServiceImpl;

public class ReflectionApplication {

    public static void main(String[] args) {
        Context context = new Context();
        SecondServiceImpl secondService = context.getBean(SecondServiceImpl.class);
        secondService.goodMethod();

        FirstServiceImpl firstService = context.getBean(FirstServiceImpl.class);
        firstService.badMethod();
    }
}