package ru.vsu.amm.java;

public class Main {

    public static void main(String[] args) {
        Fish goldie = new GoldFish("Goldie");
        Fish dory = new BlueTangFish("Dory");

        speakAndSwim(goldie);
        System.out.println();
        speakAndSwim(dory);

        System.out.println();
        areFishesEqual(goldie, dory);
        checkFishType(dory);
        checkFishType(goldie);
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
            System.out.println("They are not equal!");
        }

    }

    public static void checkFishType(Object obj) {
        if (!(obj instanceof Fish))
            System.out.println("It's not a fish!");
        else {
            System.out.print("Fish " + ((Fish) obj).getName());
            if (obj instanceof GoldFish)
                System.out.println(" is a kind of gold fish!");
            else if (obj instanceof BlueTangFish)
                System.out.println(" is a kind of blue tang fish!");

        }
    }
}
