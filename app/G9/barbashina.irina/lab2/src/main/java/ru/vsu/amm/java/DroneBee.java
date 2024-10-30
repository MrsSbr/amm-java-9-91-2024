package ru.vsu.amm.java;

public class DroneBee extends AbstractBee {
    public DroneBee(int age) {
        super("Drone", age);
    }

    @Override
    public void gatherNectar() {
        System.out.println("Drone bee cannot gather nectar");
    }

    @Override
    public void produceHoney() {
        System.out.println("Drone bee cannot produce honey");
    }

    @Override
    public void defendHive() {
        System.out.println("Drone bee defending the hive");
    }

    @Override
    public void layEggs() {
        System.out.println("Drone bee cannot laying eggs");
    }
}
