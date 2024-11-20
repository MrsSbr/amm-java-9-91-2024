package ru.vsu.amm.java.classes;

public class WorkerAnt extends Ant {

    public WorkerAnt(String name, int age) {
        super(name, age);
    }

    @Override
    public void say() {
        System.out.println("Я рабочий муравей");
    }

    @Override
    public void work() {
        System.out.println(name + " собирает еду.");
    }

    @Override
    public String toString() {
        return "WorkerAnt{name='" + name + "', age=" + age + "}";
    }
}