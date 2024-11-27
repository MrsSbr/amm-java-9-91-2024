package ru.vsu.amm.java;

public class Rook extends Fowl {

    public Rook(String name, String color) {
        super(name, color);
    }

    @Override
    public void eat() {
        System.out.println(getName() + " eats fish");
    }
}
