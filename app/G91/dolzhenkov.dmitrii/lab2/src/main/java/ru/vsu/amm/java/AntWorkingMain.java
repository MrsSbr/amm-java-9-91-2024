package ru.vsu.amm.java;

public class AntWorkingMain {
    public static void main(String[] args) {
        AbstractAnt worker = new WorkerAnt("Муравей 1", 2);
        AbstractAnt cook = new CookAnt("Муравей 2", 3);
        AbstractAnt scalist = new ScalaAnt("Муравей 3", 5);


        worker.work();
        cook.work();
        scalist.work();


        AbstractAnt anotherWorker = new WorkerAnt("Муравей 1", 2);
        System.out.println(worker.equals(anotherWorker)); // true
        System.out.println(worker.hashCode() == anotherWorker.hashCode()); // true

        if (scalist instanceof ScalaAnt) {
            System.out.println("Это скалист муравей.");
        }

        System.out.println(worker.toString());
    }
}