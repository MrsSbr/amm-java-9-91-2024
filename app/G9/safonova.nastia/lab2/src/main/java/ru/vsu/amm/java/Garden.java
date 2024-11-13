package ru.vsu.amm.java;

import java.time.LocalDate;

public class Garden {
    public static void main(String[] args) {
        Flower Rose = new Flower("Rose", 15);
        Fruit Apple = new Fruit ("Apple", 3, 10);

        Rose.isEdible();
        Apple.isEdible();
        System.out.println("\n");

        Flower Chamomile = new Flower ("Chamomile", 20);
        Fruit RedApple = new Fruit ("Apple", 5, 10);

        System.out.println("is Rose equal to Chamomile > " +
                Rose.equals(Chamomile));
        System.out.println("is Apple equal to RedApple > " +
                Apple.equals(RedApple) + "\n");

        Rose.grow();
        Apple.grow();


    }
}