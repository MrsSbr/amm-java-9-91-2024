package ru.vsu.amm.java.classes;

import static java.util.Objects.hash;

public class SoldierAnt extends Ant {

    private int countMedal = 0;

    public SoldierAnt(String name, int age, int countMedal) {
        super(name, age);
        this.countMedal = countMedal;
    }

    @Override
    public void say() {
        System.out.println("Я солдат муравей");
    }

    @Override
    public void work() {
        System.out.println(name + " защищает королеву.");
        countMedal++;
    }

    @Override
    public String toString() {
        return "SoldierAnt{name='" + name + "', age=" + age + ", countMedal=" + countMedal + "}";
    }

    @Override
    public int hashCode() {
        return hash(super.hashCode(), countMedal);
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && countMedal == ((SoldierAnt) obj).countMedal;
    }

    public void protectedQueen() {
        System.out.println(name + " спас королеву.");
    }
}