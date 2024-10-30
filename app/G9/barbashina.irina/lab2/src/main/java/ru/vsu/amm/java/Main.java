package ru.vsu.amm.java;

public class Main {
    public static void main(String[] args) {
        ActionsBee bee1 = new WorkerBee(1, 500, 200);
        ActionsBee bee2 = new DroneBee(3, true, 50);
        ActionsBee bee3 = new QueenBee(2,1000,80);

        System.out.println(bee1);
        System.out.println(bee2);
        System.out.println(bee3);
        System.out.println(bee2.equals(bee3));
        System.out.println(bee1.hashCode());

        if (bee1 instanceof WorkerBee) {
            System.out.println("bee1 is WorkerBee");
            bee1.gatherNectar();
            bee1.produceHoney();
        } else {
            System.out.println("bee1 is not WorkerBee");
        }

        if (!(bee3 instanceof DroneBee)) {
            System.out.println("bee3 is not DroneBee");
            bee3.gatherNectar();
            bee3.produceHoney();
            bee3.layEggs();
        } else {
            System.out.println("bee3 is DroneBee");
        }
    }
}