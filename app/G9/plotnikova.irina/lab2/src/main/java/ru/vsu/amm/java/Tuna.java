package ru.vsu.amm.java;

public class Tuna extends  Fish{
    public Tuna(String name, double weight) {
        super(name, weight);
    }

    @Override
    public void swim() {
        System.out.println( name + " плавает  в Атлантическом океане");
    }

    @Override
    public void eat() {
        System.out.println( name + "  употребляет в качестве пищи маленьких рыбок ");
    }


}
