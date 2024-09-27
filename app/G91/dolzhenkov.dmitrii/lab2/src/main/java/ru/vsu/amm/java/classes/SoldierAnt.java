package ru.vsu.amm.java.classes;

public class SoldierAnt extends AbstractAnt {

    public SoldierAnt(String name, int age) {
        super(name, age);
    }

    @Override
    public void work() {
        System.out.println(name + " защищает королеву.");
    }

    public void protectedQueen() {
        System.out.println(name + " спас королеву.");
    }
}