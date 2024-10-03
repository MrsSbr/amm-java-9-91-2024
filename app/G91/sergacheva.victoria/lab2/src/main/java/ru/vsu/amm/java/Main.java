package ru.vsu.amm.java;

import java.util.Objects;

public class Main {

    public static void main(String[] args) {
        Bird sparrow = new Sparrow("Sparrow", "Grey");
        Bird eagle = new Eagle("Eagle", "Brown", 2.5);
        Bird penguin = new Penguin("Penguin", "Black-white");

        System.out.println("Sparrow instanceof Sparrow: " + (sparrow instanceof Sparrow));
        System.out.println("Eagle instanceof Eagle: " + (eagle instanceof Eagle));
        System.out.println("Penguin instanceof Penguin: " + (penguin instanceof Penguin));

        System.out.println("\nInformation about birds:");
        System.out.println(sparrow);
        System.out.println(eagle);
        System.out.println(penguin);

        System.out.println("\nBird Comparison:");
        System.out.println("Sparrow equals Sparrow: " + sparrow.equals(sparrow));
        System.out.println("Sparrow equals Eagle: " + sparrow.equals(eagle));
        System.out.println("Sparrow equals Penguin: " + sparrow.equals(penguin));

        System.out.println("\nWhat do birds eat:");
        sparrow.eat();
        eagle.eat();
        penguin.eat();
    }
}

interface BirdBehavior {
    void eat();
}

abstract class Bird implements BirdBehavior {
    private String name;
    private String color;

    public Bird(String name, String color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Bird: " + name + ", color: " + color;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Bird other = (Bird) obj;
        return name.equals(other.name) && color.equals(other.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, color);
    }
}

class Sparrow extends Bird {
    public Sparrow(String name, String color) {
        super(name, color);
    }

    @Override
    public void eat() {
        System.out.println(getName() + " eats seeds");
    }
}

class Eagle extends Bird {
    private double wingspan;

    public Eagle(String name, String color, double wingspan) {
        super(name, color);
        this.wingspan = wingspan;
    }

    @Override
    public String toString() {
        return super.toString() + ", wing span: " + wingspan;
    }

    @Override
    public void eat() {
        System.out.println(getName() + " eats fish");
    }
}

class Penguin extends Bird {
    public Penguin(String name, String color) {
        super(name, color);
    }

    @Override
    public void eat() {
        System.out.println(getName() + " eats fish");
    }
}
