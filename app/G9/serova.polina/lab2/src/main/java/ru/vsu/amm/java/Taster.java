package ru.vsu.amm.java;


public class Taster extends Worker {

    public Taster(String name, double salary) {
        super(name, salary);
    }

    @Override
    public void doWork() {
        System.out.println("Tasting candies...");
    }

    @Override
    public String getPosition() {
        return "Taster";
    }

    public void evaluateDessert(String dessertName, int score) {
        System.out.println(getName() + " evaluated " + dessertName + " with a score of " + score + "/10.");
    }
}
