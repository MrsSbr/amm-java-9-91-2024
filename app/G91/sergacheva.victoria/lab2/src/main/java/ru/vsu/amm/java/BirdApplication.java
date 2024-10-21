package ru.vsu.amm.java;

public class BirdApplication {

    public static void main(String[] args) {

        Fowl raven = new Raven("Raven", "Grey");
        Fowl bullfinch = new Bullfinch("Bullfinch", "Brown", 2.5);
        Fowl rook = new Rook("Rook", "Black-white");

        System.out.println("Sparrow instanceof Sparrow: " + (raven instanceof Raven));
        System.out.println("Eagle instanceof Eagle: " + (bullfinch instanceof Bullfinch));
        System.out.println("Penguin instanceof Penguin: " + (rook instanceof Rook));

        System.out.println("\nInformation about birds:");
        System.out.println(raven);
        System.out.println(bullfinch);
        System.out.println(rook);

        System.out.println("\nBird Comparison:");
        System.out.println("Raven equals Raven: " + raven.equals(raven));
        System.out.println("Raven equals Rook: " + raven.equals(rook));
        System.out.println("Raven equals Bullfinch: " + raven.equals(bullfinch));

        System.out.println("\nWhat do birds eat:");
        raven.eat();
        rook.eat();
        bullfinch.eat();
    }
}

