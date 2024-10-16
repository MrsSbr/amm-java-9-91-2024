package ru.vsu.amm.java;

public class WorkerBee extends AbstractBee {
    public WorkerBee(int age) {
        super("Worker", age);
    }

    @Override
    public void gatherNectar() {
        System.out.println("Worker bee gathering nectar");
    }

    @Override
    public void produceHoney() {
        System.out.println("Worker bee producing honey");
    }

    @Override
    public void defendHive() {
        System.out.println("Worker bee defending the hive");
    }

    @Override
    public void layEggs() {
        System.out.println("Worker bee cannot laying eggs");
    }
}
