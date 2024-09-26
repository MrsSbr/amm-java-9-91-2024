package ru.vsu.amm.java;

class CookAnt extends AbstractAnt {

    public CookAnt(String name, int age) {
        super(name, age);
    }

    @Override
    public void work() {
        System.out.println(name + " готовит еду.");
    }
}