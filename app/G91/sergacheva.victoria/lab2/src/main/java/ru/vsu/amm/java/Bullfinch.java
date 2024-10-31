package ru.vsu.amm.java;

import java.util.Objects;

public class Bullfinch extends Fowl {

    private double wingspan;

    public Bullfinch(String name, String color, double wingspan) {
        super(name, color);
        this.wingspan = wingspan;
    }

    @Override
    public void eat() {
        System.out.println(getName() + " eats fish");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Проверка на равенство по ссылке
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // Проверка на совпадение классов
        }
        Bullfinch other = (Bullfinch) obj;
        return super.equals(other) && Double.compare(wingspan, other.wingspan) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), wingspan);
    }

    @Override
    public String toString() {
        return super.toString() + ", wing span: " + wingspan;
    }
}
