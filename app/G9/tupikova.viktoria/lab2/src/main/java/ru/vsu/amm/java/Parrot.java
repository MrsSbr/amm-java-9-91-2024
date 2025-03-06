package ru.vsu.amm.java;

import java.util.Objects;

class Parrot extends ZooAnimal {
    private String vocabulary;

    public Parrot(String name, int age, String vocabulary) {
        super(name, age);
        this.vocabulary = vocabulary;
    }

    public String getVocabulary() {
        return vocabulary;
    }

    public void setVocabulary(String vocabulary) {
        this.vocabulary = vocabulary;
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " say: " + vocabulary + " " + vocabulary);
    }

    @Override
    public void move() {
        System.out.println(getName() + " is flying");
    }

    @Override
    public String toString() {
        return "Parrot [name=" + getName() + ", age=" + getAge() + ", vocabulary=" + vocabulary + "]";
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && this.vocabulary.equals(((Parrot)o).vocabulary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAge(), vocabulary);
    }
}