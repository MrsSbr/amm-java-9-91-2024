package ru.vsu.amm.java;

public class GoldFish extends Fish {

    public GoldFish(String name) {
        super(name, "gold");
    }

    @Override
    public void speak() {
        System.out.println("I am a gold fish!");
    }

    @Override
    public String toString() {
        return super.toString() + " I am really pretty!";
    }
}
