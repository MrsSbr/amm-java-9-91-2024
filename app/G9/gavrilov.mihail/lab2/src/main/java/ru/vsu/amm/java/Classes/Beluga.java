package ru.vsu.amm.java.Classes;

import ru.vsu.amm.java.Interface.Breathable;
import ru.vsu.amm.java.Enum.Nutrition;

import java.util.Objects;

public class Beluga extends Animal implements Breathable {
    private final boolean caviar;

    public Beluga(String name, double weight, Nutrition methodOfNutrition,
                  int paws, boolean caviar) {
        super(name, weight, methodOfNutrition, paws);
        this.caviar = caviar;
    }

    @Override
    public void animalSay() {
        super.animalSay();
        System.out.println("булькает!!!\n");
    }

    @Override
    public void breathingWithGills() {
        System.out.println("Я дышу жабрами!");
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Beluga beluga)) {
            return false;
        }
        return super.equals(obj) && beluga.caviar == caviar;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), caviar);
    }

    @Override
    public String toString() {
        return super.toString() + "икра " + (caviar ? "есть!" : "нет(") + '\n';
    }
}