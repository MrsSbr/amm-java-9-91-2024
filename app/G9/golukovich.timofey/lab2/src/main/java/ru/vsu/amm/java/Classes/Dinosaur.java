package ru.vsu.amm.java.Classes;

import ru.vsu.amm.java.Interfaces.Dino;

import java.util.Objects;

public abstract class Dinosaur implements Dino {
    protected int age;
    protected String name;
    protected String habitat;

    public Dinosaur(int age, String name, String habitat) {
        this.age = age;
        this.name = name;
        this.habitat = habitat;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    public String getHabitat() {
        return habitat;
    }

    public void growUp() {
        age += 1;
    }

    @Override
    public void eat() {
        System.out.println(name + " поел!");
    }

    public String toString() {
        return "I'm a dinosaur!!!";
    }
}
