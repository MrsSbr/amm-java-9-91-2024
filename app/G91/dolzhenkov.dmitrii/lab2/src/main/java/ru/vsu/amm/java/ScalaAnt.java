package ru.vsu.amm.java;

class ScalaAnt extends AbstractAnt {

    public ScalaAnt(String name, int age) {
        super(name, age);
    }

    @Override
    public void work() {
        System.out.println(name + " пишет на Scala3 код для муравейника.");
    }
}