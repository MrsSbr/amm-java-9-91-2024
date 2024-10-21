package ru.vsu.amm.java;

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

