package ru.vsu.amm.java;

class Sparrow extends Bird {

    public Sparrow(String name, String color) {
        super(name, color);
    }

    @Override
    public void eat() {
        System.out.println(getName() + " eats seeds");
    }
}
