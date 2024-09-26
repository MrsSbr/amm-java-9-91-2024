package ru.vsu.amm.java.Classes;

public abstract class Dinosaur {
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

    public void eat() {
        System.out.println(name + " поел!");
    }
    public void saySomething() {
        System.out.println(this);
    }

    public String toString() {
        return "I'm a dinosaur!!!";
    }
}
