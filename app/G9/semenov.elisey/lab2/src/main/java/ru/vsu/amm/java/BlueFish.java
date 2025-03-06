package ru.vsu.amm.java;

import java.util.Objects;

public class BlueFish extends AquariumFish {

    private int size;

    public BlueFish(String name, int age, int size) {
        super(name, age);
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    @Override
    public void jump() {
        System.out.println("BlueFish " + getName() + " is jumping");
    }

    @Override
    public String toString() {
        return super.toString() + ", " + size;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && size == ((BlueFish) o).getSize();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getAge(), size);
    }
}