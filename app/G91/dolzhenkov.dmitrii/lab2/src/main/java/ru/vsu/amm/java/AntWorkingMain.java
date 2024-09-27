package ru.vsu.amm.java;

import ru.vsu.amm.java.classes.AbstractAnt;
import ru.vsu.amm.java.classes.SoldierAnt;
import ru.vsu.amm.java.classes.QueenAnt;
import ru.vsu.amm.java.classes.WorkerAnt;

public class AntWorkingMain {
    public static void main(String[] args) {
        AbstractAnt worker = new WorkerAnt("Муравей 1", 2);
        AbstractAnt cook = new SoldierAnt("Муравей 2", 3);
        AbstractAnt queen = new QueenAnt("Королева", 5);


        worker.work();
        cook.work();
        queen.work();


        AbstractAnt anotherWorker = new WorkerAnt("Муравей 1", 2);
        System.out.println(worker.equals(anotherWorker)); // true
        System.out.println(worker.hashCode() == anotherWorker.hashCode()); // true

        if (queen instanceof QueenAnt) {
            System.out.println("Это королева муравей.");
        }

        System.out.println(worker.toString());
    }
}