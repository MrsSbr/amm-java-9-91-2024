package ru.vsu.amm.java;

public class QueenBee extends AbstractBee {
    public QueenBee(int age) {
        super("Queen", age);
    }

    @Override
    public void gatherNectar() {
        System.out.println("Queen bee gathering nectar");
    }

    @Override
    public void produceHoney() {
        System.out.println("Queen bee producing honey");
    }

    @Override
    public void defendHive() {
        System.out.println("Queen bee defending the hive");
    }

    @Override
    public void layEggs() {
        System.out.println("Queen bee laying eggs");
    }
}
