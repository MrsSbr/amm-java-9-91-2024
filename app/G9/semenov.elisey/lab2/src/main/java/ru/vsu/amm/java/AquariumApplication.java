package ru.vsu.amm.java;

public class AquariumApplication {
    public static void main(String[] args) {
        AquariumFish firstFish = new GoldenFish("Kitty", 2, 234);
        AquariumFish secondFish = new BlueFish("Bob", 2, 234);

        firstFish.swim();
        firstFish.jump();

        secondFish.swim();
        secondFish.jump();

        System.out.println("hashes equals: " + (firstFish.hashCode() == secondFish.hashCode()));
        System.out.println("fishes equals: " + firstFish.equals(secondFish));

        if (secondFish instanceof GoldenFish) {
            System.out.println("secondFish is a GoldenFish");
        } else if (secondFish instanceof BlueFish) {
            System.out.println("secondFish is a BlueFish");
        }
    }
}