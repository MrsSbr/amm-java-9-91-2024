package ru.vsu.amm.java;

public class TaskFarm {
    public static void main(String[] args) {
        FarmAnimal cow = new Cow("Milka", 51.0);
        FarmAnimal chicken = new Chicken("Ryaba", 10);

        System.out.println("\nAnimals info: ");
        System.out.println(cow);
        System.out.println(chicken);

        System.out.println("\nWhat animals eat: ");
        cow.eat();
        chicken.eat();

        System.out.println("\nComparation: ");
        System.out.println("Cow equals Cow: " + cow.equals(cow));
        System.out.println("Cow equals Chicken: " + cow.equals(chicken));

        System.out.println("\nInstance of check: ");
        System.out.println("Cow instanceof Cow: " + (cow instanceof Cow));
        System.out.println("Cow instanceof Chicken: " + (cow instanceof Chicken));
        System.out.println("Chicken instanceof Chicken: " + (chicken instanceof Chicken));
    }
}