package ru.vsu.amm.java.classes;

import ru.vsu.amm.java.interfaces.Workable;

public abstract class Ant implements Workable {
    protected String name;
    protected int age;

    public Ant(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public void work() {
        System.out.println(name + " выполняет работу.");
    }

    @Override
    public String toString() {
        return "Ant{name='" + name + "', age=" + age + "}";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) return false;
        if (this == obj) return true;
        Ant that = (Ant) obj;
        return age == that.age && name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + age;
    }
}