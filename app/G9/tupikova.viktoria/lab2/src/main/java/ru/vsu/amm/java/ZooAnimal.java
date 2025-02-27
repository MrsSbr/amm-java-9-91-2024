package ru.vsu.amm.java;

import java.util.Objects;

public abstract class ZooAnimal implements Animal{
    private String name;
    private int age;

    public ZooAnimal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public abstract void move();

    @Override
    public String toString() {
        return "ZooAnimal [name=" + name + ", age=" + age + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ZooAnimal fish = (ZooAnimal) o;
        return name.equals(fish.getName()) && age == fish.getAge();
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }
}
