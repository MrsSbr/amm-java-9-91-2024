package ru.vsu.amm.java.Classes;

import ru.vsu.amm.java.Enum.Nutrition;

import java.util.Objects;

public class Wolf extends Animal {
    private int tailLength;

    public Wolf(String name, double weight, Nutrition methodOfNutrition,
                int paws, int tailLength){
        super(name, weight, methodOfNutrition, paws);
        this.tailLength = tailLength;
    }

    @Override
    public void animalSay() {
        super.animalSay();
        System.out.println( "Воет!\n");
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Wolf wolf)) {
            return false;
        }
        return super.equals(obj) && tailLength == wolf.tailLength;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), tailLength);
    }

    @Override
    public String toString() {
        return super.toString() + "Длина хвоста: " + tailLength + '\n';
    }
}