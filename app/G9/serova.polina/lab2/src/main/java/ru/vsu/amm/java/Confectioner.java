package ru.vsu.amm.java;


public class Confectioner extends Worker {

    public Confectioner(String name, double salary) {
        super(name, salary);
    }

    @Override
    public void doWork() {
        System.out.println("Making candies...");
    }

    @Override
    public String getPosition() {
        return "Confectioner";
    }

    public void createRecipe(String recipeName) {
        System.out.println(getName() + " is creating a recipe for " + recipeName + ".");
    }
}
