package ru.vsu.amm.java;

public class Main {

    public static void main(String[] args) {
        Context context = new Context();
        Service2 service2 = context.getBean(Service2.class);
        service2.goodMethod();
    }
}