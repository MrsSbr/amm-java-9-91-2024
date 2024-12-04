package ru.vsu.amm.java;

import ru.vsu.amm.java.context.Context;
import ru.vsu.amm.java.services.Service2;

public class Main {
    public static final String packagePath = "ru.vsu.amm.java";

    public static void main(String[] args) {
        Context context = new Context(packagePath);
        Service2 s2 = context.get(Service2.class);
        System.out.println(s2.getMessage());
    }
}