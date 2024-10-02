package ru.vsu.amm.java;

import ru.vsu.amm.java.classes.Ant;
import ru.vsu.amm.java.classes.SoldierAnt;
import ru.vsu.amm.java.classes.QueenAnt;
import ru.vsu.amm.java.classes.WorkerAnt;

public class AntWorkingMain {
    public static void main(String[] args) {
        Ant worker = new WorkerAnt("Муравей 1", 5);
        Ant cook = new SoldierAnt("Муравей 2", 3);
        Ant queen = new QueenAnt("Королева", 5, 4);

        worker.work();
        cook.work();
        queen.work();

        Ant anotherWorker = new WorkerAnt("Муравей 1", 2);
        System.out.println(worker.equals(anotherWorker)); // true
        System.out.println(worker.hashCode() == anotherWorker.hashCode()); // true

        if (queen instanceof QueenAnt) {
            System.out.println("Это королева муравей.");
        }

        QueenAnt anotherQueen = new QueenAnt("Королева", 5, 4);
        System.out.println(queen.equals(anotherQueen)); //true
        anotherQueen.makeLarvae();
        System.out.println(queen.equals(anotherQueen)); //false

        System.out.println(worker.toString());
    }
}