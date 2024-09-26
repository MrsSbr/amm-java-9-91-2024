package ru.vsu.amm.java;

class WorkerAnt extends AbstractAnt {

    public WorkerAnt(String name, int age) {
        super(name, age);
    }

    @Override
    public void work() {
        System.out.println(name + " собирает еду.");
    }
}