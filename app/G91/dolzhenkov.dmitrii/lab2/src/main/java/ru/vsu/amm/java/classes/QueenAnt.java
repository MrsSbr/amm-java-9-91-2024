package ru.vsu.amm.java.classes;

import static java.util.Objects.hash;

public class QueenAnt extends Ant {

    private int countLarvaes = 0;

    public QueenAnt(String name, int age, int countLarvaes) {
        super(name, age);
        this.countLarvaes = countLarvaes;
    }

    public void makeLarvae() {
        countLarvaes++;
        System.out.println(name + " снесла личинки.");
    }

    @Override
    public void say() {
        System.out.println("Я королева муравьев");
    }

    @Override
    public void work() {
        System.out.println(name + " царствует в муравейнике.");
    }

    @Override
    public String toString() {
        return "QueenAnt{name='" + name + "', age=" + age + ", countLarvaes=" + countLarvaes + "}";
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && countLarvaes == ((QueenAnt) obj).countLarvaes;
    }

    @Override
    public int hashCode() {
        return hash(super.hashCode(), countLarvaes);
    }
}