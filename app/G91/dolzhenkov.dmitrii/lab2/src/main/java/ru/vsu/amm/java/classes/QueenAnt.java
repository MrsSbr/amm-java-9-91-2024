package ru.vsu.amm.java.classes;

public class QueenAnt extends AbstractAnt {

    private int countLarvaes = 0;

    public QueenAnt(String name, int age) {
        super(name, age);
    }

    @Override
    public void work() {
        System.out.println(name + " царствует в муравейнике.");
    }

    public void makeLarvae() {
        countLarvaes++;
        System.out.println(name + " снесла личинки.");
    }
}