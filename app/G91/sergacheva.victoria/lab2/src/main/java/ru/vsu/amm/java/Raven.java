package ru.vsu.amm.java;

public class Raven extends Fowl {

    public Raven(String name, String color) {
        super(name, color);
    }

    @Override
    public void eat() {
        System.out.println(getName() + " eats seeds");
    }
}
