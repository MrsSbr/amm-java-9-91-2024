package ru.vsu.amm.java;


import java.util.Objects;

class Lion extends ZooAnimal {
    private int weight;

    public Lion(String name, int age, int weight) {
        super(name, age);
        this.weight = weight;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " RRRRRrrrr!");
    }

    @Override
    public void move() {
        System.out.println(getName() + " is running");
    }

    @Override
    public String toString() {
        return "Lion [name=" + getName() + ", age=" + getAge() + ", weight=" + weight + "]";
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && this.weight == ((Lion)o).weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAge(), weight);
    }
}