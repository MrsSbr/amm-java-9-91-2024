package ru.vsu.amm.java;

public class Main {

    public static void main(String[] args) {
        Fish blowy = new BlowFish("Blowy", 15);
        Fish betty = new BettaFish("Betty", 9);

        speakAndSwim(blowy);
        System.out.println();
        speakAndSwim(betty);

        System.out.println();
        areFishesEqual(blowy, betty);
        checkFishType(betty);
        checkFishType(blowy);
    }

    public static void speakAndSwim(Fish fish) {
        fish.speak();
        System.out.println(fish);
        fish.swim();
    }

    public static void areFishesEqual(Fish fish1, Fish fish2) {
        if (fish1.hashCode() == fish2.hashCode()) {
            System.out.print("They have identical hash");
            if (fish1.equals(fish2)) {
                System.out.println(" and they are equal!");
            } else {
                System.out.println(", but they are not equal!");
            }
        } else {
            System.out.println("They are not equal!!!");
        }

    }

    public static void checkFishType(Object obj) {
        if (!(obj instanceof Fish))
            System.out.println("It's not a fish!");
        else {
            System.out.print("Fish " + ((Fish) obj).getName());
            if (obj instanceof BlowFish)
                System.out.println(" is a prickly fish!");
            else if (obj instanceof BettaFish)
                System.out.println(" is an aggressive fish!");

        }
    }
}
