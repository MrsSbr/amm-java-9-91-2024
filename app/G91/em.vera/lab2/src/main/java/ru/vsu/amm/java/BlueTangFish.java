package ru.vsu.amm.java;

public class BlueTangFish extends Fish {
    public BlueTangFish(String name) {
        super(name, "blue");
    }

    @Override
    public void speak() {
        System.out.println("I am a blue tang fish!");
    }

    @Override
    public String toString() {
        return super.toString() + " I am very fast!";
    }
}
