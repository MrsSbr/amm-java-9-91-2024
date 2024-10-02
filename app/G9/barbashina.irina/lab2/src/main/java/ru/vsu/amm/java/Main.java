package ru.vsu.amm.java;

public class Main {
    public static void main(String[] args) {
        Bee bee1 = new WorkerBee(2);
        Bee bee2 = new DroneBee(1);
        Bee bee3 = new QueenBee(3);

        System.out.println(bee1);
        System.out.println(bee2.equals(bee3));
        System.out.println(bee1.hashCode());

        if (bee1 instanceof WorkerBee) {
            ((WorkerBee) bee1).defendHive();
        }

        if (bee3 instanceof QueenBee) {
            ((QueenBee) bee3).layEggs();
        }
    }
}